package lk.shaanzx.online_auction_system_backend.dto;

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
    private String name;
    private String email;
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Address must be at least 3 characters long")
    private String address;
    @Pattern(regexp = "^(0\\d{9}|\\+94\\d{9})$", message = "Invalid phone number format")
    private String contact;
    @Pattern(regexp = "^(\\d{9}V|\\d{12})$", message = "Invalid NIC number format")
    private String nic;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one number")
    private String password;
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Invalid user role format")
    private String role;
    @Pattern(regexp = "^(Active|Inactive)$", message = "Invalid user status format")
    private String status;
}