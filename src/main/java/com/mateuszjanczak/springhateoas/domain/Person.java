package com.mateuszjanczak.springhateoas.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String creditCardType;
    private String creditCardNumber;
}
