package com.yury.codingTask.points.delegate;

import com.yury.codingTask.points.repository.TransactionRepository;
import com.yury.codingTask.points.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PersistenceDelegate {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;
}
