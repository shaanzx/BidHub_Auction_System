package lk.shaanzx.online_auction_system_backend.controller;

import jakarta.validation.Valid;
import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.dto.ResponseDTO;
import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/addCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", categoryDTO));
    }

    @PutMapping(value = "/updateCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", null));
    }

    @DeleteMapping(value = "/deleteCategory")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.deleteCategory(categoryDTO.getCategoryCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", null));
    }

    @GetMapping(value = "/getCategories")
    public ResponseEntity<ResponseDTO> getCategories() {
        List<CategoryDTO> categories=categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success",categories));
    }

    @GetMapping(value = "getNextCategoryCode")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getNextCategoryCode() {
        String categoryCode=categoryService.getNextCategoryCode();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success",categoryCode));
    }
}
