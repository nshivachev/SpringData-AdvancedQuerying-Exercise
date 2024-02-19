package com.softuni.bookshopsystem.service;


import com.softuni.bookshopsystem.model.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
