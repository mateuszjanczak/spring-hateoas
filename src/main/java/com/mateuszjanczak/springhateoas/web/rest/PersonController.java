package com.mateuszjanczak.springhateoas.web.rest;

import com.mateuszjanczak.springhateoas.domain.Person;
import com.mateuszjanczak.springhateoas.service.PersonService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/persons")
    public ResponseEntity<CollectionModel<EntityModel<Person>>> getPersons() {
        List<EntityModel<Person>> personList = personService.getAll().stream()
                .map(person -> EntityModel.of(person,
                        linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel(),
                        linkTo(methodOn(PersonController.class).getPersons()).withRel("persons")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(personList,
                        linkTo(methodOn(PersonController.class).getPersons()).withSelfRel()));
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<EntityModel<Person>> getPersonById(@PathVariable int id) {
        return personService.getById(id)
                .map(person -> EntityModel.of(person,
                        linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel(),
                        linkTo(methodOn(PersonController.class).getPersons()).withRel("persons")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
