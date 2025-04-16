package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;

import java.util.List;

public interface BidCartService {
    List<BidCartDTO> getBidCartItems();

    List<BidCartDTO> getNewestBidCartItems();
}
