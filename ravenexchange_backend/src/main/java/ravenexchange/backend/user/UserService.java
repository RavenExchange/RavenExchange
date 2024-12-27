package ravenexchange.backend.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    //UserService constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String firstname, String lastname, String email, String password, String university_email) {
        //Validate user credentials
        int minPasswordLength = 6;
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

    public User loginUser(String login, String password){
        User user;
        boolean loginIsEmail = login.contains("@"); //Check if login is an email

        //Check if the login is an email or username
        if(loginIsEmail){
            if(login.contains("cmail")){
                throw new IllegalArgumentException("Login can't be cmail");
            }

            user = userRepository.findByEmail(login);
        }
        else{
            user = userRepository.findByUsername(login);
        }

        if(user == null){
            throw new IllegalArgumentException("User not found");
        }

        //Verify raw password against hashed password
        if(!verifyPassword(password, user.getPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }

        return user;
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

    public List<User> searchUser(String query, Integer offset, Integer limit){
        //Validate input
        if(query == null || query.isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty or null");
        }

        if(offset == null || offset < 0){
            throw new IllegalArgumentException("Offset must be greater than or equal to 0");
        }

        if(limit == null || limit <= 0){
            throw new IllegalArgumentException("Limit must be greater than 0");
        }

        List<User> users = userRepository.findByUsernameContaining(query);

        if(users.isEmpty() || offset >= users.size()){
            return new ArrayList<>();
        }

        return users.subList(offset, Math.min(offset + limit, users.size()));
    }
}
