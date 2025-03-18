package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.ItemService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/items")
@MultipartConfig(fileSizeThreshold = 10 * 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/addItem" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> addItem(
            @Valid
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("status") String status,
            @RequestParam("categoryCode") String categoryCode,
            @RequestParam("userId") String userId,
            @RequestParam(value = "image") MultipartFile image) {  // 🔥 Change to MultipartFile
        try {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(code);
            itemDTO.setName(name);
            itemDTO.setDescription(description);
            itemDTO.setPrice(price);
            itemDTO.setStatus(status);
            itemDTO.setCategoryCode(categoryCode);
            itemDTO.setUserId(userId);
            itemDTO.setImage(image);

            int result = itemService.addItem(itemDTO);

            if (result == VarList.Created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Success", itemDTO));
            } else if (result == VarList.Not_Acceptable) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Item already exists", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Exception: " + e.getMessage(), null));
        }
    }


    @PutMapping(value = "/updateItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateItem(
            @Valid
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("status") String status,
            @RequestParam("categoryCode") String categoryCode,
            @RequestParam("userId") String userId,
            @RequestParam(value = "image") MultipartFile image){

        try {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(code);
            itemDTO.setName(name);
            itemDTO.setDescription(description);
            itemDTO.setPrice(price);
            itemDTO.setStatus(status);
            itemDTO.setCategoryCode(categoryCode);
            itemDTO.setUserId(userId);

            if (image != null && image.isEmpty()) {
                itemDTO.setImage(image);
            }

            int response = itemService.updateItem(itemDTO);

            if (response == VarList.OK) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", itemDTO));
            } else if (response == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Item not found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Exception: " + e.getMessage(), null));
        }
    }

    @DeleteMapping(value = "/deleteItem")
    public ResponseEntity<ResponseDTO> deleteItem(@Valid @RequestBody ItemDTO itemDTO){
        if (itemService.deleteItem(itemDTO.getCode()) == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (itemService.deleteItem(itemDTO.getCode()) == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Item not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping(value = "/getItems")
    public ResponseEntity<ResponseDTO> getAllItems(){
        if (itemService.getItems() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", itemService.getItems()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping(value = "/getNextItemCode")
    public ResponseEntity<ResponseDTO> getNextItemCode(){
        String itemCode=itemService.getNextItemCode();
        if (itemCode != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success",itemCode));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
