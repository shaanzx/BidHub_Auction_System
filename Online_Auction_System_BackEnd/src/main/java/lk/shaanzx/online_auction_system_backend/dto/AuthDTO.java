package lk.shaanzx.online_auction_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO {
    private String email;
    private String token;
    private String role;
    private UUID userId;
}