package com.vehicule.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicule.models.Salle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.Optional;

import com.vehicule.models.Salle;
import com.vehicule.repositories.SalleRepository;


@CrossOrigin(origins ="*")
@RestController
public class SalleController {

    private final SalleRepository salleRepository;

    public SalleController(SalleRepository salleRepository) {
       this.salleRepository = salleRepository;
    }

    @GetMapping("salles")
    public List<Salle> findAllSalle() {
        return this.salleRepository.findAll();
    }

    @GetMapping("salles/{id}")
    public Optional<Salle> findSalle(@PathVariable("id") int id) {
        return this.salleRepository.findById(id);
    }

    @PostMapping("salles")
    public Salle saveSalle(@Validated @RequestBody Salle salle) {
        return this.salleRepository.save(salle);
    }

    @PutMapping("salles/{id}")
    public Salle updateSalle(@PathVariable("id") int id, @RequestBody Salle salle) {
        return this.salleRepository.save(salle);
    }

    @DeleteMapping("salles/{id}")
    public void deleteSalle(@PathVariable("id") int id) {
        this.salleRepository.deleteById(id);
    }

}