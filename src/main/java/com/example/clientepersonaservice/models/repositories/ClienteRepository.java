package com.example.clientepersonaservice.models.repositories;

import com.example.clientepersonaservice.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente,Long>{
}
