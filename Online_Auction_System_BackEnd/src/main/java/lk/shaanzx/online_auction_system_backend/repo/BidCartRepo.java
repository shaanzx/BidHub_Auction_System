package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidCartRepo extends JpaRepository<Bid, String> {

    @Query("SELECT new lk.shaanzx.online_auction_system_backend.dto.BidCartDTO(" +
            "i.code, i.name, i.description, i.imagePath, i.price, " +
            "b.bidCode, b.highestPrice, b.endBidDate) " +
            "FROM bid b JOIN b.itemCode i " +
            "WHERE b.endBidDate > CURRENT_TIMESTAMP")
    List<BidCartDTO> getAllActiveBidCartItems();
}

