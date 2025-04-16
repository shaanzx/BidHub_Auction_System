package lk.shaanzx.online_auction_system_backend.controller;

import lk.shaanzx.online_auction_system_backend.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auction")
@CrossOrigin(origins = "*")
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/checkWinnersManually")
    public ResponseEntity<?> checkWinnersManually() {
        auctionService.processEndedAuctions();
        return ResponseEntity.ok("Checked and notified winners.");
    }
}
