package com.poc.drug;

import org.flowable.dmn.engine.DmnEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class DrugCalculator implements Function<DrugInput, DrugOutput> {

    @Autowired
    private DmnEngine dmnEngine;

    @Override
    public DrugOutput apply(DrugInput drugInput) {
        Map<String, Object> result =
                // invoke the DMN engine service to start running the DMN on your inputs
                dmnEngine.getDmnRuleService() .createExecuteDecisionBuilder()
                        // this is the the exact DMN you want to run
                        .decisionKey("RiskRatingDecisionTable")
                        //below are the Inputs to the DMN
                        .variable("age", 20)
                        .variable("riskcategory", drugInput.getName())
                        .variable("debtreview", drugInput.getDebtReview())

                        .executeWithSingleResult(); //run the DMN
        DrugOutput output = new DrugOutput();
        output.setName(drugInput.getName());
        output.setDescription(drugInput.getDescription());
        // Below three fields are being populated from the DMN file we just ran
        output.setRouting((String) result.get("routing"));
        output.setReviewLevel((String) result.get("reviewlevel"));
        output.setReason((String) result.get("reason"));
        return output;
    }
}