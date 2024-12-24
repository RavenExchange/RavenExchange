package ravenexchange.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;

    public UserController(UserRepository userRepository){
        this.userService = new UserService(userRepository);

    }

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody User user){
        userService.createUser(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user.getUniversity_email());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
