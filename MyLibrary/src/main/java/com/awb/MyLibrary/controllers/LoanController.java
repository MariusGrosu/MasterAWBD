package com.awb.MyLibrary.controllers;

import com.awb.MyLibrary.models.Loan;
import com.awb.MyLibrary.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("")
    public String showLoans(Model model) {
        List<Loan> loans = loanService.getAllLoans();
        model.addAttribute("loans", loans);
        return "loans/list"; // Template pentru listă de împrumuturi
    }

    @GetMapping("/{id}")
    public String getLoanById(@PathVariable Long id, Model model) {
        Loan loan = loanService.getLoanById(id).orElse(null);
        model.addAttribute("loan", loan);
        return "loans/detail"; // Template pentru detalii împrumut
    }

    @PostMapping("")
    public String addLoan(@ModelAttribute("loan") Loan loan) {
        loanService.addLoan(loan);
        return "redirect:/loans";
    }

    @DeleteMapping("/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }
}
