package lk.shaanzx.online_auction_system_backend.controller;

import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*@PostMapping(value = "/addCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public*/
}
