package com.tgbotyms.moneycounterbot.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private long tgUserId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @Min(6)
    private String password;

    @Column(name = "current_balance")
    private BigDecimal currentBalance = BigDecimal.ZERO;;

    @Column(name = "days_division", columnDefinition = "integer default 30")
    @NotNull(message = ErrorMessages.NotNullMsg)
    @Min(value = 1, message = ErrorMessages.DaysLessThanOne)
    private int daysDivision = 30;

    @NotNull
    @Column(name = "calculation_date")
    private LocalDate calculationDate = LocalDate.now();

    @Column(name = "days_left", columnDefinition = "integer default 30")
    @Min(value = 1, message = ErrorMessages.DaysLessThanOne)
    @NotNull(message = ErrorMessages.NotNullMsg)
    private int daysLeft = 30;


    public User(long tgUserId) {
        this.tgUserId = tgUserId;
    }

}
