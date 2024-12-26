package ravenexchange.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    //UserController constructor
    public UserController(UserRepository userRepository){
        this.userService = new UserService(userRepository);
    }

    /**
     * Registers a new user
     *
     * @param user User object containing user information given via POST request in JSON format
     * @return Returns a JSON response containing the status and message
     */
    @PostMapping("/register")
    ResponseEntity<Map<String,Object>> register(@RequestBody User user){
        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        //Create user
        try {
            userService.createUser(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user.getUniversity_email());

            jsonResponse.put("message", "User registered successfully");
            jsonResponse.put("status", HttpStatus.CREATED);

            return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
        }
        catch (IllegalArgumentException e){ //Password length is less than minimum
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    /**
     * Verifies if a user's login credentials are correct
     *
     * @param loginRequest JSON request containing the user's login and password
     * @return Returns a JSON response containing the status and message
     */
    @PostMapping("/login")
    ResponseEntity<Map<String,Object>> login(@RequestBody Map<String, String> loginRequest){
        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            String login = loginRequest.get("login");
            String password = loginRequest.get("password");
            boolean loginIsEmail = login.contains("@"); //Check if login is an email

            userService.loginUser(login, password, loginIsEmail);

            jsonResponse.put("message", "User logged in successfully");
            jsonResponse.put("status", HttpStatus.OK);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (IllegalArgumentException e){ //Username or password is incorrect
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.UNAUTHORIZED);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
        }

    }
}
