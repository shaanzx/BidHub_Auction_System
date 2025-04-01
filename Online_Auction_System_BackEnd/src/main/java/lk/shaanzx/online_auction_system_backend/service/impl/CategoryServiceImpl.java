package lk.shaanzx.online_auction_system_backend.service.impl;

import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.entity.Category;
import lk.shaanzx.online_auction_system_backend.repo.CategoryRepo;
import lk.shaanzx.online_auction_system_backend.service.CategoryService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    CategoryServiceImpl implements CategoryService {

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
    public int addCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsById(categoryDTO.getCategoryCode())) {
            return VarList.Not_Acceptable;
        }else {
            categoryRepo.save(modelMapper.map(categoryDTO, Category.class));
            return VarList.Created;
        }
    }

    @Override
    public int updateCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsById(categoryDTO.getCategoryCode())) {
            categoryRepo.save(modelMapper.map(categoryDTO, Category.class));
            return VarList.OK;
        }else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int deleteCategory(String categoryCode) {
        if (categoryRepo.existsById(categoryCode)) {
            categoryRepo.deleteById(categoryCode);
            return VarList.OK;
        }else {
            return VarList.Not_Found;
        }
    }

    @Override
    public List<CategoryDTO> getCategories() {
        if (categoryRepo.findAll().isEmpty()) {
            return null;
        }else {
            return modelMapper.map(categoryRepo.findAll(), new TypeToken<List<CategoryDTO>>(){}.getType());
        }
    }
}
