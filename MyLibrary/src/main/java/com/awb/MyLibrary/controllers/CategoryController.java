package com.awb.MyLibrary.controllers;

import com.awb.MyLibrary.models.Category;
import com.awb.MyLibrary.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    //metoda showCategories rapsunde la solicitri top GET pentru /root. Metoda retunreaza sir string
    public String showCategories(Model model) { //obiectul Model care permite adaugarea de atribute accesibile in sabloane de view
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories); //adaugare lista de categorii in model cu denumire atribut "categories" pentru atasare la sablon
        return "categories/list"; // returnare nume template pentru generare raspuns HTTP. Este template Thymeleaf pentru listă de categorii
    }

    @GetMapping("/{id}") //placeholder pentru parametru de cautare din url
    //Metoda getCategoryById raspunde la solicitari HTTP GET pentru url-ul care contine ID
    public String getCategoryById(@PathVariable Long id, Model model) { //metoda getCategoryById returneaza un sir de tip String
        Category category = categoryService.getCategoryById(id).orElse(null);
        model.addAttribute("category", category);
        return "categories/detail"; // Poate fi un template Thymeleaf pentru detalii categorie
    }

    @PostMapping("")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
