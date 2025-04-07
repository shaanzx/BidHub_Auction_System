package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidCart {
    private String itemCode;
    private String name;
    private String description;
    private String imagePath;
    private double highestBidPrice;
    private String status;
}
