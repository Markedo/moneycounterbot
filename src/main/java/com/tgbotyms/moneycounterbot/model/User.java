package com.tgbotyms.moneycounterbot.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tg_user_id")
    private String tgUserId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "current_balance")
    private BigDecimal currentBalance = BigDecimal.ZERO;;

    @Column(name = "days_division", columnDefinition = "integer default 30")
    @NotNull
    @Min(value = 1, message = "Days division cannot be less than 1")
    private int daysDivision = 30;

    @NotNull
    @Column(name = "calculation_date")
    private LocalDate calculationDate = LocalDate.now();

    @Column(name = "days_left", columnDefinition = "integer default 30")
    @Min(value = 1, message = "Days left cannot be less than 1")
    @NotNull
    private int daysLeft = 30;


    public User(String tgUserId) {
        this.tgUserId = tgUserId;
    }
/*
    public User(String tgUserId, BigDecimal currentBalance) {
        this.tgUserId = tgUserId;
        this.currentBalance = currentBalance;
    }

    public User(String tgUserId, int daysDivision) {
        this.tgUserId = tgUserId;
        this.daysDivision = daysDivision;
    }
     */
}
