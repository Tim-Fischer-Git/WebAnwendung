package com.webanwendung.Repositorys;

import com.webanwendung.Entitys.Sighting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SightingRepository extends JpaRepository<Sighting, Long> {
    Sighting findById(long id);
}
