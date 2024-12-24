package ravenexchange.backend.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final int minPasswordLength = 6;
    private final UserRepository userRepository;

    //UserService constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String firstname, String lastname, String email, String password, String university_email) {
        //Validate user credentials
        if(password.length() < minPasswordLength){
            throw new IllegalArgumentException("Password must be at least " + minPasswordLength + " characters long");
        }

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        if(userRepository.existsByUniversityEmail(university_email)) {
            throw new IllegalArgumentException("University email already exists");
        }

        User user = new User(username, firstname, lastname, email, hashPassword(password), university_email);
        return userRepository.save(user);
    }

    /**
     * Hashes the raw password using BCrypt
     *
     * @param rawPassword Raw password to be hashed.
     * @return Returns the hashed password.
     */
    private String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * Verifies the raw password against the hashed password.
     *
     * @param rawPassword Raw password to be verified.
     * @param hashedPassword Hashed password to be verified against.
     * @return Returns true if the raw password matches the hashed password.
     */
    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
