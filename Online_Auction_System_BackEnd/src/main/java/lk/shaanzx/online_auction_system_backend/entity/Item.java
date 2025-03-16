package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "item")
public class Item {
    @Id
    private String code;
    private String name;
    private String description;
    private String imagePath;
    private double price;
    private String status;
    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category categoryCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
