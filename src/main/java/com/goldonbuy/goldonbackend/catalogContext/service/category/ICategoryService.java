package com.goldonbuy.goldonbackend.catalogContext.service.category;

import com.goldonbuy.goldonbackend.catalogContext.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);

}
