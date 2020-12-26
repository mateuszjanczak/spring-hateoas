package com.mateuszjanczak.springhateoas.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String creditCardType;
    private String creditCardNumber;
}
