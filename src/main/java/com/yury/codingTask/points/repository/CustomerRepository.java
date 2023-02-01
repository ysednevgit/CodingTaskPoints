package com.yury.codingTask.points.repository;

import com.yury.codingTask.points.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}

