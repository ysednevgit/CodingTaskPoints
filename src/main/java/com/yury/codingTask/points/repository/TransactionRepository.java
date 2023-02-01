package com.yury.codingTask.points.repository;

import com.yury.codingTask.points.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("SELECT s FROM Transaction s WHERE date >= ?1")
    List<Transaction> findAllFromDate(Date date);

}
