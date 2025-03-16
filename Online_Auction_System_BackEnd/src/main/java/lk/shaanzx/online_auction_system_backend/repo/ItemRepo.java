package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,String> {
}
