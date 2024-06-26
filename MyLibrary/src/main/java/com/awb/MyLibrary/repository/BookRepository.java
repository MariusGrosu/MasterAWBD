package com.awb.MyLibrary.repository;

import com.awb.MyLibrary.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByAuthorNameContaining(String authorName, Pageable pageable);

}
