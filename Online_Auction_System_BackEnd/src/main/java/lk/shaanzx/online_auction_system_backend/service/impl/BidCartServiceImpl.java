package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.repo.BidCartRepo;
import lk.shaanzx.online_auction_system_backend.service.BidCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidCartServiceImpl implements BidCartService {
    @Autowired
    private BidCartRepo bidCartRepo;

    @Override
    public List<BidCartDTO> getBidCartItems() {
        return bidCartRepo.getAllActiveBidCartItems();
    }
}
