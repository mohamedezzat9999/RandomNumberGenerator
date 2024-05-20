package com.example.test.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "result")
@Setter
@Getter
public class Result {
    @Id
    private Integer id;
    private Integer multipliedNumber;
}
