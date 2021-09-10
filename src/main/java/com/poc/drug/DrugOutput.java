package com.poc.drug;

import lombok.Data;

@Data
public class DrugOutput {
    protected String name;
    protected String description;
    protected boolean valid;
    protected String routing;
    protected String reviewLevel;
    protected String reason;
}