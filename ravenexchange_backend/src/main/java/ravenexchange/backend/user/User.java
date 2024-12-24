package ravenexchange.backend.user;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Table(name = "raven_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password; //Hashed

    @Column(name = "university_email")
    private String university_email;

    @Column(name = "verification_status")
    private boolean verification_status = false;

    @Column(name = "creation_timestamp")
    private Timestamp creation_timestamp;

    @Column(name = "profile_picture")
    private String profile_picture = "default.jpg";

    public User() {}

    public User(String username, String firstname, String lastname, String email, String password, String universityEmail) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password; //Hash this later
        this.university_email = universityEmail;
        creation_timestamp = Timestamp.valueOf(LocalDateTime.now());

//        verificationStatus = verifyUniversityEmail(universityEmail);
//
//        if(verificationStatus){
//            userID = createUserID();
//            creationTimestamp = LocalDateTime.now();
//        }

    }

    public String toString(){
        return "User: " + username + " " + firstname + " " + lastname + " " + email + " " + password + " " + university_email;
    }

}
