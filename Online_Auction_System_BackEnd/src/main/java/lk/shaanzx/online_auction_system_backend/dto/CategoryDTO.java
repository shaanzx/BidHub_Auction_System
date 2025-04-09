package lk.shaanzx.online_auction_system_backend.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    @Pattern(regexp = "^CAT-\\d{4}$", message = "Invalid CAT format")
    private String categoryCode;
    private String categoryName;
    private String categoryDescription;
    @Pattern(regexp = "^(Active|Inactive)$", message = "Invalid user status format")
    private String status;
}
