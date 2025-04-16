package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.entity.Bid;
import lk.shaanzx.online_auction_system_backend.entity.BidDetails;
import lk.shaanzx.online_auction_system_backend.entity.Item;
import lk.shaanzx.online_auction_system_backend.entity.Winner;
import lk.shaanzx.online_auction_system_backend.repo.BidDetailsRepo;
import lk.shaanzx.online_auction_system_backend.repo.BidRepo;
import lk.shaanzx.online_auction_system_backend.repo.ItemRepo;
import lk.shaanzx.online_auction_system_backend.repo.WinnerRepo;
import lk.shaanzx.online_auction_system_backend.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private BidDetailsRepo bidDetailsRepo;

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private WinnerRepo winnerRepo;

    @Override
    public void processEndedAuctions() {
        List<Bid> expiredBids = bidRepo.findByEndBidDateBeforeAndItemCode_Status(LocalDateTime.now(), "Approved");

        for (Bid bid : expiredBids) {
            Item item = bid.getItemCode();

            BidDetails topBid = bidDetailsRepo.findTopByBidCodeOrderByBidPriceDesc(bid.getBidCode());

            if (topBid != null && topBid.getUserId() != null) {
                // Send Email
                String userEmail = topBid.getUserId().getEmail();
                String subject = "🎉 You Won the Auction!";
                String body = String.format(
                        "Hello %s,\n\nCongratulations! You placed the highest bid of Rs. %.2f for item '%s'.\n\nThank you for participating!\n\n- BidHub Team",
                        topBid.getUserId().getName(),
                        topBid.getBidPrice(),
                        item.getName()
                );
                emailService.sendEmail(userEmail, subject, body);

                // Save Winner Details
                Winner winner = new Winner();
                winner.setItem(item);
                winner.setUser(topBid.getUserId());
                winner.setBidPrice(topBid.getBidPrice());
                winner.setBidTime(topBid.getBidDateTime());
                winner.setAwardedTime(LocalDateTime.now());

                winnerRepo.save(winner);

                // Mark as completed
                item.setStatus("Sold Out");
                itemRepo.save(item);
            }
        }
    }
}
