package lk.shaanzx.online_auction_system_backend.scheduler;

import lk.shaanzx.online_auction_system_backend.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuctionScheduler {

    @Autowired
    private AuctionService auctionService;

    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void checkAuctionWinners() {
        auctionService.processEndedAuctions();
    }
}
