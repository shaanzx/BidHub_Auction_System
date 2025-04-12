package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidCartDTO {
    // From ItemDTO
    private String itemCode;
    private String name;
    private String description;
    private String imagePath;
    private double price;

    // From BidDTO
    private String bidCode;
    private double highestBidPrice;
    private LocalDateTime bidEndTime;
}

