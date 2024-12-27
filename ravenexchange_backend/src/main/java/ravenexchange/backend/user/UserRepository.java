package ravenexchange.backend.user;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ravenexchange.backend.listing.Listing;

import java.util.List;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username); //Check if a user with the given username exists
    boolean existsByEmail(String email); //Check if a user with the given email exists
    boolean existsByUniversityEmail(String universityEmail); //Check if a user with the given university email exists
    User findByEmail(String email); //Find a user by email
    User findByUsername(String username); //Find a user by username
    List<User> findByUsernameContaining(String username); //Find users by username containing a specific string
}
