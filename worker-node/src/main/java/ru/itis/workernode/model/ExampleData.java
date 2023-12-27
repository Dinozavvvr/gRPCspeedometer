package ru.itis.workernode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExampleData {

    private int id;

    private String name;

    private String lastName;

    private Integer age;

    private LocalDate date;

    private Double height;
}
