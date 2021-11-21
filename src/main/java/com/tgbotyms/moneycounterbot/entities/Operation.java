package com.tgbotyms.moneycounterbot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;

@Component
@Entity
@Table(name = "Operations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operation extends BasicEntity {

    @NotNull
    @NotBlank
    @DecimalMin(value = "0.0")
    private BigDecimal operationAmount;

    @NotNull
    @NotBlank
    private OperationTypes operationType;

    @NotNull
    @NotBlank
    private Calendar operationDate;

    private String userComment;

    public Operation(Long id, OperationTypes operationType, BigDecimal operationAmount, Calendar operationDate) { //hope it works
        super(id);
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.operationDate = operationDate;
    }
}
