package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.constraints.Pattern;
import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

import java.util.List;

public interface BidService {
    int saveBid(ItemDTO itemDTO);

    int updateHighestBidPrice(String itemCode,Double highestPrice);

    double getHighestBidPrice(String itemCode);

    List<BidCartDTO> getAllActiveBids();
}
