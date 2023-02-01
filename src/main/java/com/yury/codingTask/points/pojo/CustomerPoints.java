package com.yury.codingTask.points.pojo;

import com.yury.codingTask.points.entity.Customer;
import lombok.Data;

import java.util.Map;

@Data
public class CustomerPoints {

    private Customer customer;

    private long totalPoints;

    //key = month number, value = points
    private Map<String, Long> monthlyPointsMap;

}

