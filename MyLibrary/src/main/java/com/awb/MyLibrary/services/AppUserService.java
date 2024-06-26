package com.awb.MyLibrary.services;


//Creare servicu AppUserService care implementeaza UserDetailsService interface
// pentru a permite ca Spring Security sa gaseasca users in baza de date

import com.awb.MyLibrary.models.AppUser;
import com.awb.MyLibrary.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Inregistrare clasa ca serviciu
@Service
//Implementare UserDetailsService pentru detaiile user
public class AppUserService implements UserDetailsService {

    //Gasire uers in db -> vom avea nevoie de repository AppUserRepository prin DI
    //Injectare dependinta "AppUserRepository" pentru accesare metode de gasire a user
    @Autowired
    private AppUserRepository appUserRepository;

    //Implementarea metodei "loadUserByUsername" interfetei UserDetailsService pentru cautare dupa mail
    //Rescriere metoda pentru folosirea autentificarii userilor cu mail. Paramentrul folosit "email"
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Gasire user care are adresa de mail
        //Apelare Repo "appUserRepository" folosind metoda de gasire prin mail "findByEmail()" cu paramentrul "email"
        //si obtinem un obiect user(appUser) de tip AppUser
        AppUser appUser = appUserRepository.findByEmail(email);

        //Verificare daca am gasit / nu un user cu acea adresa de mail

        //Daca utilizatorul este gasit (nu este null) creaza un "sprintUser" folosind metoda " User.withUsername()"
        if (appUser != null) {
            //Creeam un springUser care este egal cu User cu nume de user,
            // adresa de mail este considerata (.getEmail) ca nume de utilizator de Spring Security
            var springUser = User.withUsername(appUser.getEmail())
                    //Setam parola pentru user. Apelam metoda pe instanta "appUser"
                    .password(appUser.getPassword())
                    //Setam rolul pentru user
                    .roles(appUser.getRole())
                    //Metoda ".build() creaza obiectul "UserDetails"
                    .build();
            //Returnam springUser
            return springUser;
            //Adaugare exeptie daca utilizatorul nu este gasit
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }


    }



}
