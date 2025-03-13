package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.AuthDTO;
import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import lk.shaanzx.online_auction_system_backend.service.RegisterService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
   private RegisterService registerService;


    @PostMapping(value = "/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> saveAdmin(@Valid @RequestBody UserDTO userDTO) {
        try {
            AuthDTO authDTO = registerService.register(userDTO);
            if (authDTO != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Success", authDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping(value = "/saveItem")
    public ResponseEntity<ResponseDTO> saveItem(@Valid @RequestBody UserDTO userDTO) {
        return null;
    }

    @PostMapping(value = "/addCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return null;
    }
}
