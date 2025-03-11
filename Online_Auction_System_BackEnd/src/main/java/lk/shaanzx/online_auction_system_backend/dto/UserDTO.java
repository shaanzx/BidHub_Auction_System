package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userContact;
    private String userNic;
    private String userPassword;
    private String userRole;

}
