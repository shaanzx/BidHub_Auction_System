package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.AuthDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

    UserDTO loadUserDetailsByUsername(String username);

/*
    UserDTO searchUser(String username);
*/

    AuthDTO register(UserDTO userDTO);

}
