package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepo extends JpaRepository<Winner, Integer> {
}
