package com.tgbotyms.moneycounterbot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;

@Component
@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicEntity {

    @Column(name = "tg-user-code")
    private String tgUserCode;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "current-balance")
    private BigDecimal currentBalance;

    @Column(name = "daysDivision")
    @NotBlank
    @NotNull
    @Min(value = 1, message = "Days division cannot be less than 1")
    private int daysDivision;

    @NotNull
    @NotBlank
    private Calendar calculationDate;

    public User(String tgUserCode, BigDecimal currentBalance, int daysDivision, Calendar calculationDate) {
        this.tgUserCode = tgUserCode;
        this.currentBalance = currentBalance;
        this.daysDivision = daysDivision;
        this.calculationDate = calculationDate;
    }
}
