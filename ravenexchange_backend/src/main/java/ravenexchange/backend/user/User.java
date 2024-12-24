package ravenexchange.backend.user;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
//import lombok.Data;

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

    //Ensure creation_timestamp is set before persisting
    @PrePersist
    private void onCreate() {
        if (this.creation_timestamp == null) {
            this.creation_timestamp = Timestamp.valueOf(LocalDateTime.now());
        }
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniversity_email() {
        return university_email;
    }

    public void setUniversity_email(String university_email) {
        this.university_email = university_email;
    }

    public boolean isVerification_status() {
        return verification_status;
    }

    public void setVerification_status(boolean verification_status) {
        this.verification_status = verification_status;
    }

    public Timestamp getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(Timestamp creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public String getProfile_picture() {
        return profile_picture;
    }
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
    public String toString(){
        return "User: " + username + " " + firstname + " " + lastname + " " + email + " " + password + " " + university_email + " " + verification_status + " " + creation_timestamp + " " + profile_picture + " " + user_id;
    }
}
