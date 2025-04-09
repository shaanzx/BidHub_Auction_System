package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.BidCartDTO;
import lk.shaanzx.online_auction_system_backend.dto.BidDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.entity.Bid;
import lk.shaanzx.online_auction_system_backend.entity.BidCart;
import lk.shaanzx.online_auction_system_backend.entity.Item;
import lk.shaanzx.online_auction_system_backend.repo.BidRepo;
import lk.shaanzx.online_auction_system_backend.repo.ItemRepo;
import lk.shaanzx.online_auction_system_backend.service.BidService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String generateNextBidCode(){
        long count = bidRepo.count() + 1;
        return String.format("BID-%04d", count);
    }
    @Override
    public int saveBid(ItemDTO itemDTO , String userId) {
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

    @Override
    public int updateHighestBidPrice(String itemCode, Double highestPrice, String userId) {
        try {
            Bid bid = bidRepo.findByItemCode(itemCode);
            if (bid == null) {
                return VarList.Not_Found;
            }

            bid.setHighestPrice(highestPrice);
            bidRepo.save(bid);
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
        List<Item> items = itemRepo.findAll();
        List<Bid> bids = bidRepo.findAll();
        List<BidCartDTO> bidCartDTOs = new ArrayList<>();

        // Loop through both lists and map to BidCartDTO
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Bid bid = bids.stream()
                    .filter(b -> b.getItemCode().equals(item.getCode())) // matching the itemCode with the bid
                    .findFirst()
                    .orElse(null); // If no matching bid is found, return null

            if (bid != null && item.getStatus().equals("Approved")) { // Make sure the item is active
                BidCartDTO bidCartDTO = new BidCartDTO();
                bidCartDTO.setItemCode(item.getCode());
                bidCartDTO.setName(item.getName());
                bidCartDTO.setDescription(item.getDescription());
                bidCartDTO.setImagePath(item.getImagePath());
                bidCartDTO.setHighestBidPrice(bid.getHighestPrice());
                bidCartDTO.setStatus(item.getStatus());

                bidCartDTOs.add(bidCartDTO);
            }
        }
        System.out.println(bidCartDTOs);
        return bidCartDTOs;
    }

}
