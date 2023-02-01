package com.yury.codingTask.points.delegate;

import com.yury.codingTask.points.Response.BaseResponse;
import com.yury.codingTask.points.Response.ErrorResponse;
import com.yury.codingTask.points.Response.GetPointsResponse;
import com.yury.codingTask.points.entity.Customer;
import com.yury.codingTask.points.entity.Transaction;
import com.yury.codingTask.points.pojo.CustomerPoints;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@Data
public class PointsDelegate {

    private static final int START_MONTHS = 3;

    Logger logger = LoggerFactory.getLogger(PointsDelegate.class);

    @Autowired
    PersistenceDelegate persistenceDelegate;

    public BaseResponse getPoints() {

        try {

            GetPointsResponse response = new GetPointsResponse();

            List<CustomerPoints> customerPoints = new ArrayList<>();

            Map<Long, Customer> customersMap = getCustomersMap();

            List<String> months = getMonths();

            List<Transaction> transactions = persistenceDelegate.getTransactionRepository().findAllFromDate(getStartDate());

            Map<Long, List<Transaction>> customerTransactionsMap = getCustomerTransactionsMap(transactions, customersMap);

            for (Map.Entry<Long, List<Transaction>> entry : customerTransactionsMap.entrySet()) {
                Customer customer = customersMap.get(entry.getKey());

                customerPoints.add(getCustomerPoints(customer, entry.getValue(), months));
            }

            response.setCustomerPointsList(customerPoints);
            response.setStatus(HttpStatus.OK);

            return response;

        } catch (Exception e) {

            logger.error(e.getMessage(), e);

            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred, please try again later.");
        }

    }

    private Map<String, Long> getEmptyMonthlyPointsMap(List<String> months) {

        Map<String, Long> monthlyPointsMap = new LinkedHashMap<>();

        for (String month : months) {
            monthlyPointsMap.put(month, 0l);
        }

        return monthlyPointsMap;
    }

    private List<String> getMonths() {
        Calendar calendar = Calendar.getInstance();

        List<String> months = new ArrayList<>();

        for (int i = 0; i < START_MONTHS; i++) {
            String month = convertToLocalDate(calendar.getTime()).getMonth().toString();
            months.add(month);

            calendar.add(Calendar.MONTH, -1);
        }
        return months;
    }

    private Map<Long, Customer> getCustomersMap() {

        Map<Long, Customer> customersMap = new HashMap<>();

        for (Customer customer : persistenceDelegate.getCustomerRepository().findAll()) {

            customersMap.put(customer.getId(), customer);
        }
        return customersMap;
    }

    private Map<Long, List<Transaction>> getCustomerTransactionsMap(final List<Transaction> transactions, final Map<Long, Customer> customersMap) {

        Map<Long, List<Transaction>> customerTransactionsMap = new HashMap<>();

        for (Long customerId : customersMap.keySet()) {
            customerTransactionsMap.put(customerId, new ArrayList<>());
        }

        for (Transaction transaction : transactions) {
            customerTransactionsMap.get(transaction.getCustomerId()).add(transaction);
        }

        return customerTransactionsMap;
    }

    private CustomerPoints getCustomerPoints(final Customer customer, final List<Transaction> transactions, final List<String> months) {

        CustomerPoints customerPoints = new CustomerPoints();

        Map<String, Long> monthlyPointsMap = getEmptyMonthlyPointsMap(months);

        long totalPoints = 0;

        for (Transaction transaction : transactions) {

            String month = convertToLocalDate(transaction.getDate()).getMonth().toString();

            long points = getPoints(transaction.getAmount());

            totalPoints += points;

            monthlyPointsMap.put(month, monthlyPointsMap.get(month) + points);
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

        calendar.add(Calendar.MONTH, -START_MONTHS + 1);
        return calendar.getTime();
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
