package com.tgbotyms.moneycounterbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "operations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tg_user_id")
    private long tgUserId;

    private OperationTypes operationType;

    @NotNull(message = ErrorMessages.NotNullMsg)
    @DecimalMin(value = "0.0", message = ErrorMessages.SumLessThanZero)
    private BigDecimal operationAmount;

    @NotNull
    private Date operationDate;

    private String userComment;

    public Operation(long tgUserId, OperationTypes operationType, BigDecimal operationAmount, Date operationDate) {
        this.tgUserId = tgUserId;
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.operationDate = operationDate;
    }
}
