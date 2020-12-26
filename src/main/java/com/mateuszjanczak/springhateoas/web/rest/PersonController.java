package com.mateuszjanczak.springhateoas.web.rest;

import com.mateuszjanczak.springhateoas.domain.Person;
import com.mateuszjanczak.springhateoas.dto.PersonRequest;
import com.mateuszjanczak.springhateoas.service.PersonService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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

    @PostMapping("/persons")
    public ResponseEntity<?> savePerson(@RequestBody PersonRequest personRequest) {
        try {
            Person person = personService.save(personRequest);
            EntityModel<Person> personResource = EntityModel.of(person,
                    linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel());

            return ResponseEntity
                    .created(new URI(personResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                    .body(personResource);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Unable to create " + personRequest);
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<EntityModel<Person>> updatePerson(@PathVariable int id, @RequestBody PersonRequest personRequest) {
        personService.update(id, personRequest);
        return personService.getById(id)
                .map(person -> EntityModel.of(person,
                        linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel(),
                        linkTo(methodOn(PersonController.class).getPersons()).withRel("persons")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
