package ravenexchange.backend.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @PostMapping("/api/auth/register")
    String register(){
        return "User registered";
    }
}
