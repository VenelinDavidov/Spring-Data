package com.example.football.repository;

import com.example.football.models.entity.Stat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Positive;
import java.util.Optional;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {

    Optional<Stat> findByPassingAndShootingAndEndurance(float passing, float shooting, float endurance);
}
