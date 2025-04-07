package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.entity.Bid;
import lk.shaanzx.online_auction_system_backend.entity.BidCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepo extends JpaRepository<Bid , String> {
    @Query("SELECT b FROM bid b WHERE b.itemCode.code = :itemCode")
    Bid findByItemCode(String itemCode);
}
