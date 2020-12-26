package com.mateuszjanczak.springhateoas.service;

import com.mateuszjanczak.springhateoas.domain.Person;
import com.mateuszjanczak.springhateoas.dto.PersonRequest;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> getAll();
    Optional<Person> getById(int id);
    Person save(PersonRequest personRequest);
    Optional<Person> update(int id, PersonRequest personRequest);
}
