package lk.shaanzx.online_auction_system_backend.controller;

import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.BidCartService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/bidCart")
@CrossOrigin(origins = "*")
public class BidCartController {
    @Autowired
    private BidCartService bidCartService;

    @GetMapping(value = "getBidCartItems")
    public ResponseEntity<ResponseDTO> getBidCartItems() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", bidCartService.getBidCartItems()));
    }

    @GetMapping(value = "getNewestBidCartItems")
    public ResponseEntity<ResponseDTO> getNewestBidCartItems() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", bidCartService.getNewestBidCartItems()));
    }
}
