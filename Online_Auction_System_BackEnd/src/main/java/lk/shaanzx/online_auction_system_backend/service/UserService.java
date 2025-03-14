package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.UserDTO;

public interface UserService {

    UserDTO searchUser(String username);

    int saveUser(UserDTO userDTO);

}
