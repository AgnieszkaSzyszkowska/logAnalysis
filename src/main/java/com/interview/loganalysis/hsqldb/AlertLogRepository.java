package com.interview.loganalysis.hsqldb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertLogRepository extends CrudRepository<AlertLog, String> {
}
