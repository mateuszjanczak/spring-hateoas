package com.mateuszjanczak.springhateoas.service;

import com.mateuszjanczak.springhateoas.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAll();
    Optional<Person> getById(int id);
}
