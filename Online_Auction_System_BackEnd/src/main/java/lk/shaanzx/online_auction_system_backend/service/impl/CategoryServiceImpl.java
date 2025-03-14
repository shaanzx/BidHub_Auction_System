package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.entity.Category;
import lk.shaanzx.online_auction_system_backend.repo.CategoryRepo;
import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Category> optionalCustomer = categoryRepo.findById(categoryDTO.getCategoryCode());
        if (optionalCustomer.isPresent()) {
            Category customer = optionalCustomer.get();
            modelMapper.map(categoryDTO, customer);
            categoryRepo.save(customer);

        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public void deleteCategory(String categoryCode) {
        Optional<Category> optionalCustomer = categoryRepo.findById(String.valueOf(categoryCode));
        if (optionalCustomer.isPresent()) {
            categoryRepo.deleteById(String.valueOf(categoryCode));
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return  modelMapper.map(categoryRepo.findAll(),new TypeToken<List<CategoryDTO>>(){}.getType());
    }
}
