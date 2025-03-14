package lk.shaanzx.online_auction_system_backend.service;

import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(String categoryCode);

    List<CategoryDTO> getCategories();

    String getNextCategoryCode();
}
