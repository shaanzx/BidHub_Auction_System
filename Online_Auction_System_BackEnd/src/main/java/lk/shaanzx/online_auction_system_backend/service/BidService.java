package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.constraints.Pattern;
import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.dto.BidDTO;
import lk.shaanzx.online_auction_system_backend.dto.BidDetailsDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

import java.util.List;
import java.util.UUID;

public interface BidService {
    int saveBid(ItemDTO itemDTO);

    int updateHighestBidPrice(String bidCode,Double highestPrice, UUID userId);

    double getHighestBidPrice(String itemCode);

    List<BidCartDTO> getAllActiveBids();

    List<BidDTO> getBids();

    List<BidDetailsDTO> getAllBidDetails();

    List <BidDetailsDTO> getBidDetailsByBidCode(String bidCode);

}
