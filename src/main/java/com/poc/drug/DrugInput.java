package com.poc.drug;

import lombok.Data;

@Data
public class DrugInput {
    protected String name;
    protected String description;
    protected int age;
    protected String category;
    protected Boolean debtReview;

}