package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.constraints.Pattern;
import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

import java.util.List;

public interface BidService {
    int saveBid(ItemDTO itemDTO, String userId);

    int updateHighestBidPrice(String itemCode,Double highestPrice, String userId);

    double getHighestBidPrice(String itemCode);

    List<BidCartDTO> getAllActiveBids();
}
