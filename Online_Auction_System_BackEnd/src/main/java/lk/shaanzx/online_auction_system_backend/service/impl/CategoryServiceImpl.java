package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.entity.Category;
import lk.shaanzx.online_auction_system_backend.repo.CategoryRepo;
import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String getNextCategoryCode() {
        long count = categoryRepo.count() + 1;
        return String.format("CAT-%04d", count);
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsById(categoryDTO.getCategoryCode())) {
            throw new RuntimeException("Category already exists");
        }
        categoryRepo.save(modelMapper.map(categoryDTO, Category.class));
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {

    }

    @Override
    public void deleteCategory(String categoryCode) {

    }

    @Override
    public List<CategoryDTO> getCategories() {
        return List.of();
    }
}
