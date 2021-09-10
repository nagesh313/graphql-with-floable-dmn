package com.poc.repository;

import com.poc.domain.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends CrudRepository<Drug, Long> {
}
