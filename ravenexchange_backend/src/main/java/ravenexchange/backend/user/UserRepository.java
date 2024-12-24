package ravenexchange.backend.user;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

}
