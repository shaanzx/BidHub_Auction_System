package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidDetailsDTO {
    private int BidDetailsId;
    private double bidPrice;
    private LocalDateTime bidDateTime;
    private String BidCode;
    private UUID userId;
    private String email;
}
