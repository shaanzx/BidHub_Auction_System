package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.entity.Category;
import lk.shaanzx.online_auction_system_backend.entity.Item;
import lk.shaanzx.online_auction_system_backend.entity.User;
import lk.shaanzx.online_auction_system_backend.repo.ItemRepo;
import lk.shaanzx.online_auction_system_backend.service.ItemService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String UPLOAD_DIR = "uploads/";  // 🔥 Folder to store images

    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int addItem(ItemDTO itemDTO) {
        try {
            if (itemRepo.existsById(itemDTO.getCode())) {
                return VarList.Not_Acceptable;
            }

            // 🔥 Save Image File
            String fileName = UUID.randomUUID() + "_" + itemDTO.getImage().getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent()); // Ensure directory exists
            Files.write(filePath, itemDTO.getImage().getBytes());

            // 🔥 Map DTO to Entity
            Item item = new Item();
            item.setCode(itemDTO.getCode());
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setImagePath(filePath.toString());  // Store path, not bytes
            item.setPrice(itemDTO.getPrice());
            item.setStatus(itemDTO.getStatus());

            // 🔥 Set Category & User (Assuming Category & User exist)
            Category category = new Category();
            category.setCode(itemDTO.getCategoryCode());
            item.setCategoryCode(category);

            User user = new User();
            user.setId(UUID.fromString(itemDTO.getUserId()));
            item.setUserId(user);

            // 🔥 Save Item
            itemRepo.save(item);
            return VarList.Created;

        } catch (IOException e) {
            e.printStackTrace();
            return VarList.Bad_Gateway;
        }
    }


    @Override
    public int updateItem(ItemDTO itemDTO) {
        if (itemRepo.existsById(itemDTO.getCode())) {
            itemRepo.save(modelMapper.map(itemDTO, Item.class));
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int deleteItem(String itemCode) {
        if (itemRepo.existsById(itemCode)) {
            itemRepo.deleteById(itemCode);
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public List<ItemDTO> getItems() {
        if (itemRepo.findAll().isEmpty()) {
            return null;
        } else {
            return modelMapper.map(itemRepo.findAll(), new TypeToken<List<ItemDTO>>() {
            }.getType());
        }
    }

    @Override
    public String getNextItemCode() {
        long count = itemRepo.count() + 1;
        return String.format("ITM-%04d", count);
    }
}
