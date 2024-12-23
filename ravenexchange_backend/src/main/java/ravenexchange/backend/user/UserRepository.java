package ravenexchange.backend.user;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @PostConstruct
    public void init(){
        System.out.println("User repository bean created");
    }
}
