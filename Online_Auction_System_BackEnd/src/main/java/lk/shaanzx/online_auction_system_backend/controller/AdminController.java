package lk.shaanzx.online_auction_system_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vi/admin")
public class AdminController {
    @PostMapping("register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(){
        return "Admin";
    }
}
