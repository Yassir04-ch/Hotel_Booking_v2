package Repository;

import model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    void update(User user);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(UUID id);

    boolean existsByEmail(String email);

    List<User> findAll();

    void updateBalance(User user);

}
