package com.example.repository;

import com.example.entites.A;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ARepository extends JpaRepository<A, Long> {
}
