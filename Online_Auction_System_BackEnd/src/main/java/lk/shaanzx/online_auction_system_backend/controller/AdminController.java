package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.AuthDTO;
import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import lk.shaanzx.online_auction_system_backend.service.UserService;
import lk.shaanzx.online_auction_system_backend.util.JwtUtil;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
   private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody  UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    authDTO.setRole(userDTO.getRole());
                    authDTO.setUserId(userDTO.getUserId());
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping(value = "/getUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getUsers() {
        if (userService.getUsers() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", userService.getUsers()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "No data found", null));
        }
    }

    @PutMapping(value = "/updateUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        if (userService.updateUser(userDTO) == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (userService.updateUser(userDTO) == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PutMapping(value = "/updateUserStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUserStatus(@Valid @RequestBody UserDTO userDTO) {
        if (userService.updateUserStatus(userDTO) == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (userService.updateUserStatus(userDTO) == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @DeleteMapping(value = "/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getEmail());
        if (userService.deleteUser(userDTO.getEmail()) == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (userService.deleteUser(userDTO.getEmail()) == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
