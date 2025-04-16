package lk.shaanzx.online_auction_system_backend.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    int addItem(ItemDTO itemDTO);

    int updateItem(ItemDTO itemDTO);

    int deleteItem(String itemCode);

    List<ItemDTO> getItems();

    String getNextItemCode();

    int updateItemStatus(@Valid ItemDTO itemDTO);

    List<ItemDTO> getItemById(@Pattern(regexp = "^ITM-\\d{4}$", message = "Invalid ITM format") String code);

/*    int approveItem(String itemCode);

    int rejectItem(String itemCode);

    int inactivateItem(String itemCode);

    int reactivateItem(String itemCode);*/
}
