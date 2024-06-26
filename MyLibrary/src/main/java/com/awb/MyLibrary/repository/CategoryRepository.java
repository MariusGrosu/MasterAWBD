package com.awb.MyLibrary.repository;

import com.awb.MyLibrary.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
