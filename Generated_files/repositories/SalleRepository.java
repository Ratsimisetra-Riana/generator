package com.vehicule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicule.models.Salle;

public interface SalleRepository extends JpaRepository<Salle,Integer> {

}