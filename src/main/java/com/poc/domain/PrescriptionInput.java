package com.poc.domain;

import lombok.Data;

import java.util.List;

@Data
public class PrescriptionInput {
    private Long id;
    private String name;
    private String description;
    private List<DrugInput> DrugList;
}
