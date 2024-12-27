package ravenexchange.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

            jsonResponse.put("message", "success");
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

            userService.loginUser(login, password);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (IllegalArgumentException e){ //Username or password is incorrect
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.UNAUTHORIZED);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonResponse);
        }

    }

    /**
     * Searches for users by username
     *
     * @param query Username to search for
     * @param offset Number of users to skip
     * @param limit Number of users to get
     * @return Returns a JSON response containing the status, message, number of users, and a list of users
     */
    @GetMapping("/search")
    ResponseEntity<Map<String, Object>> searchListing(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {

        Map<String, Object> jsonResponse = new HashMap<>(); //JSON response map

        try {
            List<User> users = userService.searchUser(query, offset, limit);

            jsonResponse.put("message", "success");
            jsonResponse.put("status", HttpStatus.OK);
            jsonResponse.put("size", users.size());
            jsonResponse.put("data", users);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }
        catch (Exception e){
            jsonResponse.put("message", e.getMessage());
            jsonResponse.put("status", HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }
}
