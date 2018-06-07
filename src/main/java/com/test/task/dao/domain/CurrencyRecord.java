package com.test.task.dao.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "currency_data")
@EqualsAndHashCode(exclude = "id")
public class CurrencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String currencyId;
    private int nominal;
    private LocalDate date = LocalDate.now();
    private double value;
    private String charCode;
    private int numCode;
    private String name;
}
