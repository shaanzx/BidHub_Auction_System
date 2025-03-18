package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "bid")
public class Bid {
    @Id
    private String bidCode;
    private LocalDateTime bidStartTime;
    private LocalDateTime endBidDate;
    private double highestPrice;

    @ManyToOne
    @JoinColumn(name = "item_code")
    private Item itemCode;
}
