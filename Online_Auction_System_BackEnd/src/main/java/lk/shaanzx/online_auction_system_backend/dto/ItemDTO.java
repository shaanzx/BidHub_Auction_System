package lk.shaanzx.online_auction_system_backend.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    @Pattern(regexp = "^ITM-\\d{4}$", message = "Invalid ITM format")
    private String code;
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must be at least 3 characters long")
    private String name;
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Description must be at least 3 characters long")
    private String description;
    private MultipartFile image;
    private double price;
    @Pattern(regexp = "^(Pending|Approved|Disapproved|On Bid|Sold Out)$", message = "Invalid status format")
    private String status;
    @Pattern(regexp = "^CAT-\\d{4}$", message = "Invalid CAT format")
    private String categoryCode;
    private String userId;
}
