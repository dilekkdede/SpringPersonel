package com.person.repository;

import com.person.entites.B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BRepository extends JpaRepository<B, Long> {

    @Query(value = "select b from B b where b.id =:id")
    B getByIdB(long id);
}
