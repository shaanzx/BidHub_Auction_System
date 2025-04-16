package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.BidService;
import lk.shaanzx.online_auction_system_backend.service.ItemService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB

    @Autowired
    private ItemService itemService;
    @Autowired
    private BidService bidService;

    @PostMapping(value = "/addItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ResponseDTO> addItem(
            @Valid
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("status") String status,
            @RequestParam("categoryCode") String categoryCode,
            @RequestParam("userId") String userId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            // Validate image size
            if (image != null && !image.isEmpty()) {
                if (image.getSize() > MAX_IMAGE_SIZE) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body(new ResponseDTO(VarList.Payload_Too_Large, "Image size exceeds maximum limit (10MB)", null));
                }

                if (!image.getContentType().startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body(new ResponseDTO(VarList.Unsupported_Media_Type, "Only image files are allowed", null));
                }
            }

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
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error processing request: " + e.getMessage(), null));
        }
    }



    @PutMapping(value = "/updateItem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ResponseDTO> getAllItems(){
        if (itemService.getItems() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", itemService.getItems()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping(value = "/getNextItemCode")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ResponseDTO> getNextItemCode(){
        String itemCode=itemService.getNextItemCode();
        if (itemCode != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success",itemCode));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PutMapping(value = "/updateItemStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateItemStatus(@Valid @RequestBody ItemDTO itemDTO) {
        int status = itemService.updateItemStatus(itemDTO);

        if ("Approved".equals(itemDTO.getStatus())) {
            switch (status) {
                case VarList.OK:
                    int response = bidService.saveBid(itemDTO);
                    return switch (response) {
                        case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDTO(VarList.Created, "Bid Add Successfully", null));
                        case VarList.Not_Acceptable -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                .body(new ResponseDTO(VarList.Not_Acceptable, "Bid already exists", null));
                        default -> ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseDTO(VarList.OK, "Item Status Updated and Bid Add Successfully", null));
                    };

                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Item not found", null));

                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        } else {
            // If status is not Approved, we only update item status, no bidding
            switch (status) {
                case VarList.OK:
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ResponseDTO(VarList.OK, "Item Status Updated", null));

                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "Item not found", null));

                default:
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }
    }

    @GetMapping(value = "/getItemById")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<ResponseDTO> getItemById(@Valid @RequestBody ItemDTO itemDTO) {
        List<ItemDTO> item = itemService.getItemById(itemDTO.getCode());
        if (item != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", item));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
