package com.poc;

import com.poc.domain.Drug;
import com.poc.domain.Prescription;
import com.poc.repository.DrugRepository;
import com.poc.repository.PrescriptionRepository;
//import org.flowable.dmn.api.DmnDecisionService;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class PocApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocApplication.class, args);
    }

    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Bean
    public Filter OpenFilter() {
        return new OpenEntityManagerInViewFilter();
    }

    //    @Autowired
//    private DmnDecisionService ruleService;
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> customProcessEngineConfigurer() {
        return engineConfiguration -> {
            engineConfiguration.setValidateFlowable5EntitiesEnabled(false);
        };
    }

    @PostConstruct
    public void init() {
        Drug drug1 = Drug.builder().name("Drug1").build();
        Drug drug2 = Drug.builder().name("Drug2").build();
        Drug drug3 = Drug.builder().name("Drug3").build();
        Drug drug4 = Drug.builder().name("Drug4").build();
        Drug drug5 = Drug.builder().name("Drug5").build();
        drugRepository.saveAll(Arrays.asList(drug1, drug2, drug3, drug4, drug5));
        Prescription prescription1 = Prescription.builder().name("Prescription1").drugList(Arrays.asList(drug1, drug2)).build();
        Prescription prescription2 = Prescription.builder().name("Prescription2").drugList(Arrays.asList(drug3)).build();
        Prescription prescription3 = Prescription.builder().name("Prescription3").drugList(Arrays.asList(drug4, drug5)).build();
        Prescription prescription4 = Prescription.builder().name("Prescription4").build();
        prescriptionRepository.saveAll(Arrays.asList(prescription1, prescription2, prescription3, prescription4));
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("inputVariable1", 1);
//        variables.put("inputVariable2", "test1");
//        Map<String, Object> result = ruleService.createExecuteDecisionBuilder().decisionKey("simple").variables(variables).executeWithSingleResult();
//        System.out.println(result);
    }
}
