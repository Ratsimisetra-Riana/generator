package com.vehicule.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicule.models.Examen;
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

import com.vehicule.models.Examen;
import com.vehicule.repositories.ExamenRepository;


@CrossOrigin(origins ="*")
@RestController
public class ExamenController {

    private final ExamenRepository examenRepository;

    public ExamenController(ExamenRepository examenRepository) {
       this.examenRepository = examenRepository;
    }

    @GetMapping("examens")
    public List<Examen> findAllExamen() {
        return this.examenRepository.findAll();
    }

    @GetMapping("examens/{id}")
    public Optional<Examen> findExamen(@PathVariable("id") int id) {
        return this.examenRepository.findById(id);
    }

    @PostMapping("examens")
    public Examen saveExamen(@Validated @RequestBody Examen examen) {
        return this.examenRepository.save(examen);
    }

    @PutMapping("examens/{id}")
    public Examen updateExamen(@PathVariable("id") int id, @RequestBody Examen examen) {
        return this.examenRepository.save(examen);
    }

    @DeleteMapping("examens/{id}")
    public void deleteExamen(@PathVariable("id") int id) {
        this.examenRepository.deleteById(id);
    }

}