package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

public interface BidService {
    int saveBid(ItemDTO itemDTO);

    int updateBid(String itemCode,Double highestPrice);
}
