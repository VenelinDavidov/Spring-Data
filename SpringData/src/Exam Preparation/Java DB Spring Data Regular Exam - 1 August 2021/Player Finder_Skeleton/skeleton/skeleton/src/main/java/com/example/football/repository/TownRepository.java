package com.example.football.repository;


import com.example.football.models.entity.Town;
import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.Size;
import java.util.Optional;

@Registered
public interface TownRepository extends CrudRepository<Town, Long> {

    Optional<Town> findByName(String name);
}
