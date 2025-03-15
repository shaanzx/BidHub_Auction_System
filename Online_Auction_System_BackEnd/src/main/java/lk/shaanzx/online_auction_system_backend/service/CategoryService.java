package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    int addCategory(CategoryDTO categoryDTO);

    int updateCategory(CategoryDTO categoryDTO);

    int deleteCategory(String categoryCode);

    List<CategoryDTO> getCategories();

    String getNextCategoryCode();
}
