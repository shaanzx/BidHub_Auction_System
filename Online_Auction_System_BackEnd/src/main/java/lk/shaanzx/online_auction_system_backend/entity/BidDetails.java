package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "bid_details")
public class BidDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bidDetailsId;
    private double bidPrice;
    private LocalDateTime bidDateTime;
    private UUID userId;
    private String bidCode;
}
