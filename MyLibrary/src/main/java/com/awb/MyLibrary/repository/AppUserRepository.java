package com.awb.MyLibrary.repository;


//Creare repository pentru a ne permite citirea utilizatorilod din db // creare AppUserRepository interface

import com.awb.MyLibrary.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

//Trebuie sa extinda interfata JpaRepository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    //Creare metoda care ajuta la gasirea unui utilizator dupa adresa de mail

    public AppUser findByEmail(String email);

    //Nu este nevoie sa implementam interfata pentru ca va fi implementata de Spring JPA
}
