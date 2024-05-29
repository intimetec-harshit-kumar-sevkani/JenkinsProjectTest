package com.example.springEmployee.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

    private  String userId;
    private String name;
    private String email;
}
