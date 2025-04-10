package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.BidService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping(value = "/addBid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> addBid(@Valid @RequestBody ItemDTO itemDTO) {
        if (bidService.saveBid(itemDTO) == VarList.Created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", itemDTO));
        } else if (bidService.saveBid(itemDTO) == VarList.Not_Acceptable) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Bid already exists", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PutMapping(value = "/updateHighestBidPrice")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateHighestBidPrice(@Valid @RequestBody ItemDTO itemDTO, @RequestParam("userId") String userId) {
        int result = bidService.updateHighestBidPrice(itemDTO.getCode(), itemDTO.getPrice(),userId);
        return switch (result) {
            case VarList.OK -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Success", itemDTO));
            case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "BID not found", null));
            default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        };
    }

    @GetMapping(value = "/getHighestBidPrice")
    public ResponseEntity<ResponseDTO> getHighestBidPrice(@Valid @RequestBody ItemDTO itemDTO) {
        double highestBidPrice = bidService.getHighestBidPrice(itemDTO.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", highestBidPrice));
    }

    @GetMapping(value = "getAllActiveBids")
    public ResponseEntity<ResponseDTO> getAllActiveBids() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", bidService.getAllActiveBids()));
    }
}
