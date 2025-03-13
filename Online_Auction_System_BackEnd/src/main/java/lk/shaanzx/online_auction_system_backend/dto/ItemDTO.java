package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private int itemCode;
    private String name;
    private String description;
    private byte[] image;
    private double price;
    private String status;
    private String categoryCode;
    private String userId;
}
