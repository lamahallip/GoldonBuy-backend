package com.goldonbuy.goldonbackend.catalogContext.service.category;

import com.goldonbuy.goldonbackend.catalogContext.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
}
