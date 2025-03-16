package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    int addItem(ItemDTO itemDTO);

    int updateItem(ItemDTO itemDTO);

    int deleteItem(String itemCode);

    List<ItemDTO> getItems();

    String getNextItemCode();

/*    int approveItem(String itemCode);

    int rejectItem(String itemCode);

    int inactivateItem(String itemCode);

    int reactivateItem(String itemCode);*/
}
