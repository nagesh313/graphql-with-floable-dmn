package com.poc.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.poc.domain.Drug;
import com.poc.domain.DrugInput;
import com.poc.domain.Prescription;
import com.poc.domain.PrescriptionInput;
import com.poc.repository.DrugRepository;
import com.poc.repository.PrescriptionRepository;
import graphql.GraphQLException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(PrescriptionInput input) {
        return prescriptionRepository.save(getPrescription(input));
    }

    private Prescription getPrescription(PrescriptionInput prescriptionInput) {
        return Prescription.builder()
                .id(prescriptionInput.getId())
                .name(prescriptionInput.getName())
                .description(prescriptionInput.getDescription())
                .drugList(getDrugsList(prescriptionInput.getDrugList()))
                .build();
    }


    private List<Drug> getDrugsList(@NotNull List<DrugInput> drugsList) {
        List<Drug> drugList = new ArrayList<>();
        for (DrugInput input : drugsList) {
            drugList.add(getDrug(input));
        }
        drugRepository.saveAll(drugList);

        return drugList;
    }

    public Prescription updatePrescription(PrescriptionInput prescriptionInput) {
        Prescription prescription = getPrescription(prescriptionInput);
        Optional<Prescription> targetPrescription = prescriptionRepository.findById(prescription.getId());
        if (targetPrescription.isPresent()) {
            return prescriptionRepository.save(prescription);
        }
        throw new GraphQLException("Prescription id " + prescription.getId() + " does not exist.");
    }

    public Drug createDrug(DrugInput input) {
        return drugRepository.save(getDrug(input));
    }

    private Drug getDrug(DrugInput drugInput) {
        return Drug.builder()
                .name(drugInput.getName())
                .description(drugInput.getDescription())
                .build();
    }

    public Drug updateDrug(DrugInput drugInput) {
        Drug drug = getDrug(drugInput);
        Optional<Drug> targetDrug = drugRepository.findById(drug.getId());
        if (targetDrug.isPresent()) {
            return drugRepository.save(drug);
        }
        throw new GraphQLException("Drug id " + drug.getId() + " does not exist.");
    }
}
