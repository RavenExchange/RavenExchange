package ravenexchange.backend.user;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity
@Table(name = "raven_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password; //Hashed (BCrypt)

    @Column(name = "university_email")
    private String universityEmail;

    @Column(name = "verification_status")
    private boolean verificationStatus = false;

    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @Column(name = "profile_picture")
    private String profilePicture = "default.jpg";

    @Column(name = "listings")
    private ArrayList<Integer> listings; //List of listing IDs

    public User() {}

    public User(String username, String firstname, String lastname, String email, String password, String university_email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password; //Hashed (BCrypt)
        this.universityEmail = university_email;


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
        creationTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public long getUser_id() {
        return userId;
    }

    public void setUser_id(long userId) {
        this.userId = userId;
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
        return universityEmail;
    }

    public void setUniversity_email(String university_email) {
        this.universityEmail = university_email;
    }

    public boolean isVerification_status() {
        return verificationStatus;
    }

    public void setVerification_status(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Timestamp getCreation_timestamp() {
        return creationTimestamp;
    }

    public void setCreation_timestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getProfile_picture() {
        return profilePicture;
    }
    public void setProfile_picture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String toString(){
        return "User: " + username + " " + firstname + " " + lastname + " " + email + " " + password + " " + universityEmail + " " + verificationStatus + " " + creationTimestamp + " " + profilePicture + " " + userId;
    }
}
