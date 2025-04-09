package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    void deleteByEmail(String email);
}
