package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "item_code")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double bidPrice;

    private LocalDateTime bidTime;

    private LocalDateTime awardedTime;
}
