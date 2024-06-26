package com.awb.MyLibrary.controllers;
import com.awb.MyLibrary.models.AppUser;
import com.awb.MyLibrary.models.RegisterDto;
import com.awb.MyLibrary.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Creare controler care ne permite sa facem register
//Adnotam "@Controller"
@Controller
public class RegisterController {


    //Creare obiect de tip Logger pentru a loga mesajele
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);


    @Autowired
    //Injectare dependinta "AppUserRepository"
    private AppUserRepository appUserRepository;

    //Creare ruta pentru afisare formular de inregistrare
    @GetMapping("/register") // ruta "/register" va fi accesibila folosit metoda Get
    //Metoda de register care necesita un Model ce ne permite sa trimitel datele catre pagina "register"
    //In "model" adaugam un obiect de tip "RegisterDto" pe care il asociem cu formularul de "regiser"
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        //Cand afisam registru gol setam pe false
        model.addAttribute("success", false);
        return "register";
    }

    //Creare ruta / "register" ce va fi folosita folosind metoda HTTP post
    @PostMapping("/register")
    //Metoda de inregistrare avand model pentru a permite trimiterea datelor catre pagina
    public String register(
            //"Model" Trimite datele catre pagina
            Model model,
            //model registerDtop = datele transmise din formular si apoi validate
            @Valid @ModelAttribute RegisterDto registerDto,

            //rezultatul disponibil din validare va fi in obiectul "result"
            BindingResult result
    ) {

        //Verifica deaca parola din password este aceeasi cu parola din confirmPassword
        //Daca parola trimisa de "registerDto nu este egala cu parola de confirmare
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            //adaugam o eroare la obiectul "result" care este de tip FiledError. Eroarea este legata de "registerDto"
            result.addError(
                    new FieldError("registerDto", "confirmPassword" //numele campului de confirmare
                    , "Parola si Confirma Parola nu se potrivesc") //mesaj de eroare
            );
        }

        //Verificare daca adresa de mail este folosita sau nu.
        //Verificam daca un user "appUser" are aceasta adresa de mail folosind metoda"getEmail"
        AppUser appUser = appUserRepository.findByEmail(registerDto.getEmail());
        //Daca userul "appUser" nu este null, inseamna ca adresa de mail este folosita
        if (appUser != null){
            //Adaugam eroare pe obiectul "result"
            result.addError(
                    new FieldError("registerDto", "email" //campul folosit al "registerDto"
                    , "Email address is already used")
            );
        }

        //Daca avem eroare de validare returnam pagina "register" fara a crea contul
        if (result.hasErrors()){
            return "register";
        }
        //Daca nu exista eroare creem userul. Adaugam block-ul try catch unde se creeaza userul
        try {
            //creare cont nou
            var bCryptEncoder = new BCryptPasswordEncoder(); // creare obiect nou bCryptEncoder de tipul "BCryptPasswordEncoder"
            // pentru permiterea codificarii parolei

            //Creare user nou
            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName()); //folosind datele trimise care sund disponibile in "registerDto"
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
            newUser.setRole("client");
            //creare cont user
            appUserRepository.save(newUser);

            //Stergere formular pentru a avea unul gol
            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);
            //Adaugare logger
            logger.info("Userul cu email-ul: {} a fost adaugat",  newUser.getEmail());
        }
        //daca avem o exceptie adaugam o eroare
        catch (Exception ex){
            //eroare pe obiectul "result" si asociem eroarea la un camp din registerDto
            result.addError(
                    new FieldError("registerDto", "firstName"
                    , ex.getMessage())
            );
        }

        return "register"; //pagina
    }



}
