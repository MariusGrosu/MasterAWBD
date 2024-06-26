package com.awb.MyLibrary.controllers;

import com.awb.MyLibrary.models.Author;
import com.awb.MyLibrary.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    //Definire Log
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    //Metoda afisare lista autori:
    //Primește o cerere GET la url-ul de baxa
    //Obtine toti autorii folosind serviciul authorService
    //Adauga lista de autori in model
    //Returnare view pentru afisare "authors/list"
    @GetMapping("")
    public String showAuthors(Model model) { //Returnare string si primire parametru tip "Model"
        List<Author> authors = authorService.getAllAuthors(); //metoda pentru lista autori
        model.addAttribute("authors", authors); //adaugare lista autori ca atribut la model,
        // folosit pentru transfer date catre "View"
        return "authors/list"; //returnare string ca nume pentru view
    }

    //Log pe metoda
    //Metoda afisare detalii autor pe baza de ID
    // Primire cerere GET la url "/id" fiind unic
    //Folosim serviciu pentru obtinerea id-ului
    //Adaugare obiect "author" in model
    //Returnare view pentru afisare
    @GetMapping("/{id}") //id - este dinamic folosit la apelarea id-ului
    //metoda getAuthorById
    //metoda ce retunreaza un string si primeste 2 param
    public String getAuthorById(@PathVariable Long id, Model model) { //extragere valoare din id dinamic url si asociere in parametru id
        try {//"Model model" - instantiere obiect de tip Model pentru adaugare atribute pentru View
            //Folosim serviciul "authorService" pentru obtinere author prin id, daca nu e gasit afisam null
            Author author = authorService.getAuthorById(id).orElse(null);
            //Adaugare obiect "author" ca atribut in model pentru transfer date catre view.
            model.addAttribute("author", author);
            return "authors/detail"; // //returnare string ca nume pentru view
        }catch (Exception e){
                //Gestionare exceptie si adaugare mesaj de eroare in model
                model.addAttribute("error", "A aparut o eroare la gasirea autorului");
                //Returnare view pentru eroare
                log.error("Eroare", e.getMessage(), e );
                return "error";
            }
    }

    //Metoda de adaugare autor folosind cerere HTTP POST
    @PostMapping("") //metoda "addAuthor" va fi apelata cand se face cerere POST la url-ul specificat, fiind ruta de baza a controlerului
    //Returneaza un string si primeste un parametru
    //"@ModelAttribute("author") Author author" - extrage datele din formular si le asociaza obiectului de tip "Author"
    public String addAuthor(@ModelAttribute("author") Author author) {
        //Folosim serviciul "authorService" pentru adaugare autor in DB
        authorService.addAuthor(author);
        //Redirectionare folosita la prevenirea retrimiterii formularului daca este reincarcata pagina
        return "redirect:/authors"; ////returnare string si redirect ca nume pentru view
    }



    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return "redirect:/authors";
    }
}
