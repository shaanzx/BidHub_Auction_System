package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.BidDTO;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.entity.Bid;
import lk.shaanzx.online_auction_system_backend.repo.BidRepo;
import lk.shaanzx.online_auction_system_backend.service.BidService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepo bidRepo;

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

    @Override
    public int updateHighestBidPrice(String itemCode, Double highestPrice) {
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
}
