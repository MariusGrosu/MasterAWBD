package com.awb.MyLibrary.controllers;
import com.awb.MyLibrary.models.Author;
import com.awb.MyLibrary.models.Book;
import com.awb.MyLibrary.models.Category;
import com.awb.MyLibrary.services.AuthorService;
import com.awb.MyLibrary.services.BookService;
import com.awb.MyLibrary.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookControllerTest {


    //Instantiere controller pentru test/ Configurare instanta a obiectului si a mock-urilor pentru izolare logica de dependinte
    //Injectare "mocks" in instanta "BookController". Faciliteaza simularea dependintelor folosite de controller
    @InjectMocks
    private BookController bookController;


    //Creare instanta Mock pentru clasa BookService
    //Simuleaza comportamentul ei fara a depinde de implementarea reala
    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Mock
    private CategoryService categoryService;


    //Nu depinde de implementarea din Spring MVC
    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBook() {
        // Given
        String title = "Amintiri din Copilarie Test";
        String authorName = "Ion Creanga Test";
        String categoryName = "Memorii";

        // Creare autor simulat

        Author mockedAuthor = new Author();
        mockedAuthor.setName(authorName);

        // Creare categorie simulata

        Category mockedCategory = new Category();
        mockedCategory.setName(categoryName);


        // Creare carte simulata cu date de intrare

        Book mockedBook = new Book();
        mockedBook.setTitle(title);
        mockedBook.setAuthor(mockedAuthor);
        mockedBook.setCategory(mockedCategory);

        // Mock comportament serviciu autor si categorii
        // "when" Metoda statica pentru a configura comportamentul Mock-urilor cand se apeleaza metoda cu parametru
        when(authorService.createAuthor(authorName)).thenReturn(mockedAuthor); //.thenReturn(mockedAuthor) - intorace mockedAuthor
        when(categoryService.createCategory(categoryName)).thenReturn(mockedCategory);

        // When
        String result = bookController.addBook(title, authorName, categoryName);

        // Then
    //Verificam daca metoda "createAuthor"  din "authorService" a fost apelata cu argumentul "authorName"
        verify(authorService).createAuthor(authorName);

    //Verificam daca metoda "createCategory"  din "categoryService" a fost apelata cu argumentul "categoryName"
        verify(categoryService).createCategory(categoryName);

        //Verificare daca metoda
        //Folosim "(any(Book.class)" pentru a verifica general orice obiect de tip Book e adaugat

        verify(bookService).addBook(any(Book.class));



        // Verificare daca rezultatul este redirect catre url "/book"
        assertEquals("redirect:/book", result);

    }
}
