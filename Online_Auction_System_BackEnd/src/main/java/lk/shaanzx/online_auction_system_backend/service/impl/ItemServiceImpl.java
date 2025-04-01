package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.ItemDTO;
import lk.shaanzx.online_auction_system_backend.entity.Category;
import lk.shaanzx.online_auction_system_backend.entity.Item;
import lk.shaanzx.online_auction_system_backend.entity.User;
import lk.shaanzx.online_auction_system_backend.repo.ItemRepo;
import lk.shaanzx.online_auction_system_backend.service.ItemService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String UPLOAD_DIR = "uploads/";


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

            String fileName = UUID.randomUUID() + "_" + itemDTO.getImage().getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent()); // Ensure directory exists
            Files.write(filePath, itemDTO.getImage().getBytes());

            Item item = new Item();
            item.setCode(itemDTO.getCode());
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setImagePath(filePath.toString());  // Store path, not bytes
            item.setPrice(itemDTO.getPrice());
            item.setStatus(itemDTO.getStatus());

            Category category = new Category();
            category.setCode(itemDTO.getCategoryCode());
            item.setCategoryCode(category);


            User user = new User();
            user.setId(UUID.fromString(itemDTO.getUserId()));
            item.setUserId(user);

            itemRepo.save(item);
            return VarList.Created;

        } catch (IOException e) {
            e.printStackTrace();
            return VarList.Bad_Gateway;
        }
    }


    @Override
    public int updateItem(ItemDTO itemDTO) {
        try {
            Optional<Item> optionalItem = itemRepo.findById(itemDTO.getCode());
            if (optionalItem.isEmpty()) {
                return VarList.Not_Found;
            }

            Item item = optionalItem.get();

            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            item.setStatus(itemDTO.getStatus());

            Category category = new Category();
            category.setCode(itemDTO.getCategoryCode());
            item.setCategoryCode(category);

            User user = new User();
            user.setId(UUID.fromString(itemDTO.getUserId()));
            item.setUserId(user);

            if (itemDTO.getImage() != null && !itemDTO.getImage().isEmpty()) {
                if (item.getImagePath() != null) {
                    Path oldImagePath = Paths.get(item.getImagePath());
                    Files.deleteIfExists(oldImagePath);
                }

                String fileName = UUID.randomUUID() + "_" + itemDTO.getImage().getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, itemDTO.getImage().getBytes());
                item.setImagePath(filePath.toString());
            }

            itemRepo.save(item);
            return VarList.OK;

        } catch (IOException e) {
            e.printStackTrace();
            return VarList.Bad_Gateway;
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
        return itemRepo.findAll().stream().map(item -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(item.getCode());
            itemDTO.setName(item.getName());
            itemDTO.setDescription(item.getDescription());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setStatus(item.getStatus());
            itemDTO.setCategoryCode(item.getCategoryCode().getCode());
            itemDTO.setUserId(item.getUserId().getId().toString());

            if (item.getImagePath() != null) {
                itemDTO.setImagePath("http://localhost:8080/uploads/" + item.getImagePath());
            } else {
                itemDTO.setImagePath(null);
            }

            return itemDTO;
        }).collect(Collectors.toList());
    }



    @Override
    public String getNextItemCode() {
        long count = itemRepo.count() + 1;
        return String.format("ITM-%04d", count);
    }
}
