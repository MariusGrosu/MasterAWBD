package com.awb.MyLibrary.services;
import com.awb.MyLibrary.models.Author;
import com.awb.MyLibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    // Metodă pentru a crea un autor pe baza numelui
    public Author createAuthor(String authorName) {
        Author author = new Author();
        author.setName(authorName);
        return authorRepository.save(author);
    }
}
