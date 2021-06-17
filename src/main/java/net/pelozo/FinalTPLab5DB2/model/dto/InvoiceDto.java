package net.pelozo.FinalTPLab5DB2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto{

    private Long id;

    private Boolean isPaid;

    private Boolean isDue;

    private LocalDate dueDate;

    private LocalDate issueDate;

    private Float firstReading;

    private Float lastReading;

    private Float totalConsumptionKWH;

    private Float totalAmount;

    private ResidenceDto residence;

}
