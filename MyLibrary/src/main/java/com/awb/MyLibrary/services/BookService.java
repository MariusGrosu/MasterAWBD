package com.awb.MyLibrary.services;


import com.awb.MyLibrary.models.Book;
import com.awb.MyLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {



    //DI a dependintei bookRepository in clasa
    @Autowired
    //variabila de instanta a clasei "bookRepository" va fi injectata cu o instanta a clasei "BookRepository"
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    //Metoda de obtinere carte din DB folosind ID
    //Folosim metoda ".findById" din bookRepository pentru cautare carte dupa ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Metoda pentru DELETE
    public void deleteBook(Long id) {
        // Implementează logica pentru ștergerea cărții din repository
        bookRepository.deleteById(id);
    }


    //Creare metoda de UPDATE
    // Metoda pentru actualizarea titlului unei cărți în baza de date
    public void updateBookTitle(Long id, String newTitle) {
        // Cautam cartea in DB folosind ID-ul dat
        Book book = bookRepository.findById(id) // Cautam o carte in DB după ID
                .orElseThrow(() -> new RuntimeException("Cartea cu ID-ul dat nu exista: " + id));
        // Daca nu gasim o carte cu ID-ul dat, punem o exceptie de tip "RuntimeException" cu un mesaj.

        // Actualizam titlul cartii cu noul titlu
        book.setTitle(newTitle); // Setam titlu cartii cu nou titlu primit ca parametru

        // Salvam obiectul "book" cu titlu actualizat in DB ca modificarile sa fie persisetente.
        bookRepository.save(book); // Salvam cartea actualizata in db
    }



    //Implementare paginare

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }



    //Implementare filtrare dupa autor
    // Creare metoda de cautare carte dupa numele autorului si returnare pagina din rezultatele gasitex
    public Page<Book> getBooksByAuthor(String authorName, Pageable pageable) {
        return bookRepository.findByAuthorNameContaining(authorName, pageable);
    }





}