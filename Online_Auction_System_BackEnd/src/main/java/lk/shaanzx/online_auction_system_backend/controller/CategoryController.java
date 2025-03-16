package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/addCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        int response = categoryService.addCategory(categoryDTO);
        if (response == VarList.Created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", null));
        } else if (response == VarList.Not_Acceptable) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Category already exists", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PutMapping(value = "/updateCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        int response = categoryService.updateCategory(categoryDTO);
        if (response == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (response == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Category not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @DeleteMapping(value = "/deleteCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        int response = categoryService.deleteCategory(categoryDTO.getCategoryCode());
        if (response == VarList.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", null));
        } else if (response == VarList.Not_Found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Category not found", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping(value = "/getCategories")
    public ResponseEntity<ResponseDTO> getCategories() {
       if (categoryService.getCategories() != null) {
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", categoryService.getCategories()));
       } else {
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "No data found", null));
       }
    }

    @GetMapping(value = "getNextCategoryCode")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getNextCategoryCode() {
        String categoryCode=categoryService.getNextCategoryCode();
        if (categoryCode != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success",categoryCode));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
