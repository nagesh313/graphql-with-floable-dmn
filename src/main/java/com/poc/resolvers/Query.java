package com.poc.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.poc.domain.Drug;
import com.poc.domain.Prescription;
import com.poc.repository.DrugRepository;
import com.poc.repository.PrescriptionRepository;
import com.poc.drug.DrugCalculator;
import com.poc.drug.DrugInput;
import com.poc.drug.DrugOutput;
import graphql.GraphQLException;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {
    private RuntimeService runtimeService;

    @Autowired
    public Query(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DrugCalculator drugCalculator;

    public Prescription prescription(Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        if (prescription.isPresent())
            return prescription.get();
        throw new GraphQLException("Prescription does not exist");
    }

    public List<Prescription> prescriptionList() {
        return (List<Prescription>) prescriptionRepository.findAll();
    }

    public DrugOutput drug(Long id) {
        //Here we are fetching the drug information from the database by ID using JPA
        Optional<Drug> drug = drugRepository.findById(id);
        if (drug.isPresent()) {
            Drug drug1 = drug.get();
            DrugInput input = new DrugInput();
            input.setName(drug1.getName());
            input.setDescription(drug1.getDescription());
            //This is the part where we invoke the DMN rule to populate the rest of the fields of the DrugOutput
            DrugOutput output = drugCalculator.apply(input);
            return output;
        }
        throw new GraphQLException("Drug does not exist");
    }

    public List<Drug> drugList() {
        return (List<Drug>) drugRepository.findAll();
    }
}
