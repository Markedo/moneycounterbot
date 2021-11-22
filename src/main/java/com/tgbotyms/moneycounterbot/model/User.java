package com.tgbotyms.moneycounterbot.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicEntity {

    @Column(name = "tg-user-id")
    private String tgUserId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "current-balance")
    private BigDecimal currentBalance;

    @Column(name = "daysDivision", columnDefinition = "integer default 30")
    @NotBlank
    @NotNull
    @Min(value = 1, message = "Days division cannot be less than 1")
    private int daysDivision;

    @NotNull
    @NotBlank
    private Date calculationDate;

    public User(String tgUserCode, BigDecimal currentBalance, int daysDivision, Date calculationDate) {
        this.tgUserId = tgUserCode;
        this.currentBalance = currentBalance;
        this.daysDivision = daysDivision;
        this.calculationDate = calculationDate;
    }
}
