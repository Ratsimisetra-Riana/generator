package com.vehicule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicule.models.Examen;

public interface ExamenRepository extends JpaRepository<Examen,Integer> {

}