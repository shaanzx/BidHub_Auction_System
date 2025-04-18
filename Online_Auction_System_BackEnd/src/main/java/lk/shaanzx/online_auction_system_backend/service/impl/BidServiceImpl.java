package lk.shaanzx.online_auction_system_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.dto.BidDTO;
import lk.shaanzx.online_auction_system_backend.dto.BidDetailsDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.entity.*;
import lk.shaanzx.online_auction_system_backend.repo.BidDetailsRepo;
import lk.shaanzx.online_auction_system_backend.repo.BidRepo;
import lk.shaanzx.online_auction_system_backend.repo.ItemRepo;
import lk.shaanzx.online_auction_system_backend.repo.UserRepository;
import lk.shaanzx.online_auction_system_backend.service.BidService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private BidDetailsRepo bidDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String generateNextBidCode(){
        long count = bidRepo.count() + 1;
        return String.format("BID-%04d", count);
    }
    @Override
    public int saveBid(ItemDTO itemDTO) {
        try {
            if (bidRepo.existsById(itemDTO.getCode())) {
                return VarList.Not_Acceptable;
            }
            String nextCode = generateNextBidCode();
            BidDTO bidDTO = new BidDTO();
            bidDTO.setBidCode(nextCode);
            bidDTO.setBidStartTime(LocalDateTime.now());
            bidDTO.setBidEndTime(bidDTO.getBidStartTime().plusDays(5));
            bidDTO.setHighestBidPrice(itemDTO.getPrice());
            bidDTO.setItemCode(itemDTO.getCode());

                bidRepo.save(modelMapper.map(bidDTO, Bid.class));
                return VarList.Created;
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }

    @Transactional
    @Override
    public int updateHighestBidPrice(String bidCode, Double highestPrice, UUID userId, String email) {
        try {
            // Validate inputs
            if (bidCode == null || highestPrice == null || userId == null) {
                return VarList.Not_Acceptable;
            }

            // Use Optional to safely handle nulls
            Optional<Bid> optionalBid = bidRepo.findById(bidCode);
            if (optionalBid.isEmpty()) {
                return VarList.Not_Found;
            }

            Bid bid = optionalBid.get();

            // Check for higher price
            if (highestPrice <= bid.getHighestPrice()) {
                return VarList.Not_Acceptable;
            }

            // Update highest price
            bid.setHighestPrice(highestPrice);
            bidRepo.save(bid);

            // Save bid details
            BidDetails bidDetails = new BidDetails();
            bidDetails.setBidCode(bid.getBidCode());
            bidDetails.setBidPrice(highestPrice);
            User user = new User();
            user.setId(userId);
            bidDetails.setUserId(user);
            bidDetails.setEmail(email);
            bidDetails.setBidDateTime(LocalDateTime.now());

            bidDetailsRepo.save(bidDetails);

            return VarList.OK;

        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public double getHighestBidPrice(String itemCode) {
        try {
            Bid bid = bidRepo.findByItemCode(itemCode);
            if (bid == null) {
                return VarList.Not_Found;
            }
            return bid.getHighestPrice();
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public List<BidCartDTO> getAllActiveBids() {
        return List.of();
    }


    @Override
    public List<BidDTO> getBids() {
        return modelMapper.map(bidRepo.findAll(), new TypeToken<List<BidDTO>>(){}.getType());
    }

    @Override
    public List<BidDetailsDTO> getAllBidDetails() {
        return modelMapper.map(bidDetailsRepo.findAll(), new TypeToken<List<BidDetailsDTO>>(){}.getType());
    }

    @Override
    public List<BidDetailsDTO> getBidDetailsByBidCode(String bidCode) {
        return modelMapper.map(bidDetailsRepo.findByBidCode(bidCode), new TypeToken<List<BidDetailsDTO>>(){}.getType());
    }

    @Override
    public List<BidDetailsDTO> getBidDetailsBtEmail(String email) {
        return modelMapper.map(bidDetailsRepo.findByEmail(email), new TypeToken<List<BidDetailsDTO>>(){}.getType());
    }
}
