package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidCartDTO {
    private String itemCode;
    private String name;
    private String description;
    private String imagePath;
    private double highestBidPrice;
}
