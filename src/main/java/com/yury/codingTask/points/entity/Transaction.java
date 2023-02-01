package com.yury.codingTask.points.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    private Date date;

    @NotNull
    private Long customerId;

    public Transaction() {
    }
}
