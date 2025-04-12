package lk.shaanzx.online_auction_system_backend.repo;

import lk.shaanzx.online_auction_system_backend.entity.BidDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidDetailsRepo extends JpaRepository<BidDetails, String> {

    // Bid code එක අනුව bid details සොයන්න
    List<BidDetails> findByBidCode(String bidCode);

    // Optional: Bid code එක exist වෙනවද කියලා check කරන්න
    boolean existsByBidCode(String bidCode);
}
