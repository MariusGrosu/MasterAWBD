package com.awb.MyLibrary.controllers;
import com.awb.MyLibrary.models.Author;
import com.awb.MyLibrary.models.Book;
import com.awb.MyLibrary.models.Category;
import com.awb.MyLibrary.repository.BookRepository;
import com.awb.MyLibrary.services.AuthorService;
import com.awb.MyLibrary.services.BookService;
import com.awb.MyLibrary.services.CategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Controller

public class BookController {


    //Creare obiect de tip Logger pentru a loga mesajele
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired //Injectare automata a depentintelor
    //variabila de instanta a clasei "categoryService" va fi injectata cu o instanta a clasei "CategoryService"
    private CategoryService categoryService;

    @Autowired  //Injectare automata a depentintelor
    //variabila de instanta a clasei "bookRepository" va fi injectata cu o instanta a clasei "BookRepository"
    private BookRepository bookRepository;

    @Autowired  //Injectare automata a depentintelor
    //variabila de instanta a clasei "authorService" va fi injectata cu o instanta a clasei "AuthorService"
    private AuthorService authorService;
    @Autowired  //Injectare automata a depentintelor
    //variabila de instanta a clasei "bookService" va fi injectata cu o instanta a clasei "BookService"
    private BookService bookService;


    // Metoda de adaugare carte in system cand user-ul trimite datele prin formularul de POST
    @PostMapping("/book")
    // @PostMaping/"book" - metoda care raspunde la cererile de POST trimite catre ruta "/book}", id = param dinamic
    //Folosim @RequestParam , parametru pentru intreducer ein DB
    public String addBook(@RequestParam String title, @RequestParam String authorName, @RequestParam String categoryName) {
        // Creare autor - apelare serviciu pentru a crea un autor
        Author author = authorService.createAuthor(authorName);
        // Creare categorie - apelare serviciu creare categorie
        Category category = categoryService.createCategory(categoryName);

        //Creare obiect book folosind titlul introdus, autorul si categoria creati
        Book book = new Book(title, author, category);
        //Adaugare carte folosind metoda "addBook din "bookService"
        bookService.addBook(book);
        //Mesaj de informare tip log
        log.info("Carte adaugata: {}", book.getTitle());
        //Dupa adaugare userul este redirecitonat catre /book
        return "redirect:/book";
    }


    // Metoda stergere carte
    // Configurare metoda "deleteBook" primeste cerere de POST pentru stergere
    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        //Gestionare exceptie
        try {
            // Apelam metoda din serviciul "bookService" pentru a sterge cartea cu ID dorit
            bookService.deleteBook(id);
            //Mesaj de informare tip log
            log.info("Cartea cu ID {} a fost stearsa ", id);
            return "redirect:/book"; // Redirectionare catre pagina dupa delete
        }catch (Exception e){ // In caz de eroare, logam detalii despre eroare pentru verificare
            //Gestionarea erorilor
            log.error("Eroare la stergerea cartii cu ID {}: {}", id, e.getMessage(), e);
            return "redirect:/book?error"; //Redirectionare catre pagina cu parametru de eroare
        }
    }




    //Paginare
    //Metoda de tip GET care raspunde la cererile HTTP pe url-ul "/book"
    @GetMapping("/book")
    //Folosim obiectul Model pentru transimitere date catre sablonul folosit pentru afisare pagina.
    //Cu "model" adaugam atribute care sunt accesate in sablon
    public String showBooksPage(Model model, @RequestParam(defaultValue = "0") int page) { //Param de tip querry string care specifica rezultatele din pagina afisata
        //Default e 0 daca nu se specifica pagina

        //Creare obiect "Pageable" folosind "PageRequest.of(page, 10)"
        //Cu el afisam pagina "page" avand maxim 10 inregistrari in pagina
        Pageable pageable = PageRequest.of(page, 10);

        // Folosim serviciu pentru a obtine o lista de carit (Page<Book>) cu parametrii de paginare din "pageable"
        Page<Book> booksPage = bookService.getAllBooks(pageable);
        //Adaugare lista carti "booksPage" in obiectul "Model" cu cheia "books". Lista va fi disponibila in sablon pentru ieterare si afisare
        model.addAttribute("books", booksPage);
        ////returnare string ca nume pentru view
        return "book";
    }


    //Metoda raspunde la cereri de tip GET pe url-ul "/books/search"
// Filtrare dupa nume autor
    @GetMapping("/books/search")
    public String searchBooksByAuthor(
            //Parametru folosit pentru a primi numele autorului
            // Default valoarea are un sir gol
            @RequestParam(defaultValue = "") String authorName, //Param de tip querry string care face cautare
            Model model, // Folosim obiectul Model pentru a transmite date către template-ul Thymeleaf pentru a fi afisat.
            //Parametru de indicare a paginii pentru paginarea rezultatelor. Default pagina 0 (prima pagina)
            @RequestParam(defaultValue = "0") int page
    ) {
        try {
            // Creare obiect Pageable cu pagina dorina si dimensiunea paginii
            // "page" numarul paginii , dimenisunea paginii 10, listare nr de carti pe pagina
            Pageable pageable = PageRequest.of(page, 10);
            //Apelare serviciu "bookService" pentru cautare dupa numele autorului folosind metoda (getBooksByAuthor)
            // pentru a obtine cu "pageable" o pagina de rezultate
            // Logare cerere de cautare
            log.info("Cautarea cartii cu autorul: {} la pagina: {}", authorName, page);

            //Lista de book apelare serviciu pentru a obtine pagina de book pentru autorul specificat
            Page<Book> books = bookService.getBooksByAuthor(authorName, pageable);
            //Adaugare lista "books" in obiectul "Model" , pentru accesare si afisare in sablon Thymeleaf.
            model.addAttribute("books", books);
            //Adaugare nume autor  model pentru pentru afisare nume in formularul de search / in pagina
            model.addAttribute("authorName", authorName);
            //Returnare nume template Thymeleaf pentru afisare rezultat cautat
            //Rezultatul este afisat folosind sablonul Thymeleaf cu numele book.html
            return "book";
            //Tratarea eroirolor
        } catch (Exception e) {
            model.addAttribute("error", "A apărut o eroare la căutarea cărților");
            log.error("Eroare la cautarea cartilor pentru autorul {}: {}", authorName, e.getMessage(), e);

            return "error";

        }

    }



    //Metoda pentru cererile de update
    //Metoda raspunde la cereri de tip POST pe url "/book/update/{id}" cu parametru de cale variabil "id"
    @PostMapping("/book/update/{id}")
    public String updateBook(@PathVariable Long id, @RequestParam String newTitle) {
        try {
            //Apelam serviciul pentru update titlu carte cu id-ul "id" si cu noul titlu "newTitle"
            bookService.updateBookTitle(id, newTitle);

            //Inregistram log-ul ca actualizarea e facuta
            log.info("Cartea cu ID {} a fost actulizata cu noul titlu: {}", id, newTitle);
            return "redirect:/book"; //redirect catre url "/book"
        } catch (Exception e) {
            //Inregistrare log de eroare daca apare exceptie la actulaizare
            log.error("Eroare la actualizarea cartii cu ID {}: {}", id, e.getMessage(), e);
            //Redirectionam catre "book" cu parametru error
            return "redirect:/book?error";
        }
    }
}
