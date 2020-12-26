package com.mateuszjanczak.springhateoas.service;

import com.mateuszjanczak.springhateoas.domain.Person;
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
}
