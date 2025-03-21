package com.example.repository;

import com.example.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

    //query -> Entity üzerinde
    //nativequery ->Tablo üzerinde
    //namedquery -> Entity üzerinde

    @Query(value = "select * from personel p", nativeQuery = true)
    List<Personel> personelListesi();

    @Query(value = "select * from  personel p where p.id =:id2 or p.id =:id11", nativeQuery = true)
    List<Personel> persnelListsiID2ve11(long id2,long id11);

}
