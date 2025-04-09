package lk.shaanzx.online_auction_system_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "category")
public class Category {
    @Id
    @Column(name = "category_code")
    private String code;
    @Column(name = "category_name")
    private String name;
    @Column(name = "category_description")
    private String description;
    @Column(name = "category_status")
    private String status;
}
