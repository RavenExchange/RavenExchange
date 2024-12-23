package ravenexchange.backend.user;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class User {
    private int userID;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password; //Hashed
    private String universityEmail;
    private boolean verificationStatus;
    private LocalDateTime creationTimestamp;
    private String profilePicture;

    public User(String username, String firstname, String lastname, String email, String password, String universityEmail) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password; //Hash this later
        this.universityEmail = universityEmail;

        verificationStatus = verifyUniversityEmail(universityEmail);

        if(verificationStatus){
            userID = createUserID();
            creationTimestamp = LocalDateTime.now();
        }

    }

    private int createUserID() {
        // Increment from last userID
        return -1;
    }

    private boolean verifyUniversityEmail(String universityEmail){
        return true;
    }
}
