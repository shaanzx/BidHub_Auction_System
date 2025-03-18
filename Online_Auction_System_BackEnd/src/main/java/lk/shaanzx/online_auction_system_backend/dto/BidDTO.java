package lk.shaanzx.online_auction_system_backend.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BidDTO {
    @Pattern(regexp = "^BID-\\d{4}$", message = "Invalid BID format")
    private String bidCode;
    private LocalDateTime bidStartTime;
    private LocalDateTime bidEndTime;
    private double highestBidPrice;
    @Pattern(regexp = "^ITM-\\d{4}$", message = "Invalid ITM format")
    private String itemCode;
}
