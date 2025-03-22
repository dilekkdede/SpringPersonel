package com.example.repository;

import com.example.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonelJPARepository extends JpaRepository<Personel, Long> {

    //query -> Entity üzerinde
    //nativequery ->Tablo üzerinde
    //namedquery -> Entity üzerinde

    @Query(value = "select p from Personel p")
    List<Personel> personelListesi();

    @Query(value = "select p from Personel p where p.id =:id2 or p.id =:id11")
    List<Personel> personelListesiid2veyaid11jpa(long id2 , long id11);

    @Query(value = "select p from Personel p where p.status =:status")
    List<Personel> personelListesiStatusJpa(int status);





}
