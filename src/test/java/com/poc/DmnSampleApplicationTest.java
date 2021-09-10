package com.poc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.HashMap;
import java.util.Map;

import com.poc.PocApplication;
//import org.flowable.dmn.api.DmnDecisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PocApplication.class)
public class DmnSampleApplicationTest {

//    @Autowired
//    private DmnDecisionService ruleService;

    @Test
    public void contextLoads() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("inputVariable1", 1);
        variables.put("inputVariable2", "test1");
//        Map<String, Object> result = ruleService.createExecuteDecisionBuilder().decisionKey("simple").variables(variables).executeWithSingleResult();

//        assertThat(result).contains(entry("outputVariable1", "result1"));

    }
}