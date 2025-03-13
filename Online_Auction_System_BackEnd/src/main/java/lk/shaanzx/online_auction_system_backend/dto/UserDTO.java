package lk.shaanzx.online_auction_system_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must be at least 3 characters long")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    @NotBlank(message = "Name is required")
    private String userName;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format")
    private String userEmail;
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Address must be at least 3 characters long")
    private String userAddress;
    @Pattern(regexp = "^(0\\d{9}|\\+94\\d{9})$", message = "Invalid phone number format")
    private String userContact;
    @Pattern(regexp = "^(\\d{9}V|\\d{12})$", message = "Invalid NIC number format")
    private String userNIC;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")
    private String userPassword;
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Invalid user role format")
    private String userRole;
}