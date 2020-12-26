package com.mateuszjanczak.springhateoas.service;

import com.mateuszjanczak.springhateoas.domain.Person;
import com.mateuszjanczak.springhateoas.dto.PersonRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    ArrayList<Person> personList;

    public PersonServiceImpl() {
        this.personList = new ArrayList<>();
        personList.add(Person.builder().id(1).firstName("Jan").lastName("Kowalski").email("jankowalski@gmail.com").gender("Male").creditCardType("mastercard").creditCardNumber("1234123412341234").build());
        personList.add(Person.builder().id(2).firstName("Adam").lastName("Nowak").email("adamnowak@outlook.com").gender("Male").creditCardType("visa").creditCardNumber("4321432112341234").build());
        personList.add(Person.builder().id(3).firstName("Jolanta").lastName("Kwiatkowska").email("jolantakwiatkowska@gmail.com").gender("Female").creditCardType("mastercard").creditCardNumber("4321432143214321").build());
    }

    @Override
    public List<Person> getAll() {
        return personList;
    }

    @Override
    public Optional<Person> getById(int id) {
        return personList.stream().filter(person -> person.getId() == id).findFirst();
    }

    @Override
    public Person save(PersonRequest personRequest) {
        int ID = personList.isEmpty() ? 1 : personList.stream().mapToInt(Person::getId).max().getAsInt() + 1;

        Person person = Person.builder()
                .id(ID)
                .firstName(personRequest.getFirstName())
                .lastName(personRequest.getLastName())
                .email(personRequest.getEmail())
                .gender(personRequest.getGender())
                .creditCardType(personRequest.getCreditCardType())
                .creditCardNumber(personRequest.getCreditCardNumber())
                .build();

        personList.add(person);

        return person;
    }

    @Override
    public Optional<Person> update(int id, PersonRequest personRequest) {
        return personList.stream()
                .filter(person -> person.getId() == id)
                .peek(person -> {
                    person.setFirstName(personRequest.getFirstName());
                    person.setLastName(personRequest.getLastName());
                    person.setGender(personRequest.getGender());
                    person.setCreditCardType(personRequest.getCreditCardType());
                    person.setCreditCardNumber(personRequest.getCreditCardNumber());
                })
                .findFirst();
    }
}
