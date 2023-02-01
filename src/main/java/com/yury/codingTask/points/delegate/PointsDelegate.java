package com.yury.codingTask.points.delegate;

import com.yury.codingTask.points.Response.GetPointsResponse;
import com.yury.codingTask.points.entity.Customer;
import com.yury.codingTask.points.entity.Transaction;
import com.yury.codingTask.points.pojo.CustomerPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PointsDelegate {

    private static final int START_MONTHS = 3;

    @Autowired
    PersistenceDelegate persistenceDelegate;

    public GetPointsResponse getPoints() {

        GetPointsResponse response = new GetPointsResponse();

        List<CustomerPoints> customerPoints = new ArrayList<>();

        Map<Long, Customer> customersMap = getCustomersMap();

        Map<Long, List<Transaction>> customerTransactionsMap = getCustomerTransactionsMap(persistenceDelegate.getTransactionRepository().findAllFromDate(getStartDate()));

        for (Map.Entry<Long, List<Transaction>> entry : customerTransactionsMap.entrySet()) {
            Customer customer = customersMap.get(entry.getKey());

            customerPoints.add(getCustomerPoints(customer, entry.getValue()));
        }

        response.setCustomerPointsList(customerPoints);

        return response;
    }

    private Map<Long, Customer> getCustomersMap() {

        Map<Long, Customer> customersMap = new HashMap<>();

        for (Customer customer : persistenceDelegate.getCustomerRepository().findAll()) {

            customersMap.put(customer.getId(), customer);
        }
        return customersMap;
    }

    private Map<Long, List<Transaction>> getCustomerTransactionsMap(final List<Transaction> transactions) {
        Map<Long, List<Transaction>> customerTransactionsMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (customerTransactionsMap.containsKey(transaction.getCustomerId())) {
                customerTransactionsMap.get(transaction.getCustomerId()).add(transaction);
            } else {
                List<Transaction> transactionsList = new ArrayList<>();
                transactionsList.add(transaction);

                customerTransactionsMap.put(transaction.getCustomerId(), transactionsList);
            }
        }
        return customerTransactionsMap;
    }

    private CustomerPoints getCustomerPoints(final Customer customer, final List<Transaction> transactions) {

        CustomerPoints customerPoints = new CustomerPoints();

        Map<Integer, Long> monthlyPointsMap = new TreeMap<>();

        long totalPoints = 0;

        for (Transaction transaction : transactions) {

            int month = convertToLocalDate(transaction.getDate()).getMonthValue();

            long points = getPoints(transaction.getAmount());

            totalPoints += points;

            if (monthlyPointsMap.containsKey(month)) {
                monthlyPointsMap.put(month, monthlyPointsMap.get(month) + points);
            } else {
                monthlyPointsMap.put(month, points);
            }
        }

        customerPoints.setCustomer(customer);
        customerPoints.setTotalPoints(totalPoints);
        customerPoints.setMonthlyPointsMap(monthlyPointsMap);

        return customerPoints;
    }

    private long getPoints(long amount) {
        long points = 0;

        if (amount > 100) {
            points = points + 2 * (amount - 100);
        }
        if (amount > 50) {
            points = points + Math.min(points + (amount - 50), 50);
        }

        return points;
    }

    private Date getStartDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -START_MONTHS);
        return calendar.getTime();
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
