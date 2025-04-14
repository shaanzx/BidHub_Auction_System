package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO searchUser(String username);

    int saveUser(UserDTO userDTO);

    List<UserDTO> getUsers();

    int deleteUser(String email);

    int updateUser(UserDTO userDTO);

    int updateUserStatus(@Valid UserDTO userDTO);

    UserDTO getUserById(String email);

    int updateUserByEmail(@Valid UserDTO userDTO);
}
