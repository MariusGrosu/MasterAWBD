package com.awb.MyLibrary.repository;

import com.awb.MyLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
