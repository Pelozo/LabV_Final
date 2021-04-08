package net.pelozo.FinalTPLab5DB2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Invoice {

    @Id
    private Long id;

    @NotNull(message = "isPaid cannot be null")
    private Boolean isPaid;

    @NotNull(message = "dueDate cannot be null")
    private LocalTime dueDate;

    @ManyToOne
    @NotNull(message = "client cannot be null")
    private Client client;

    @ManyToOne
    @NotNull(message = "residence cannot be null")
    private Residence residence;

    @NotNull(message = "firstReading cannot be null")
    @Min(value = 0,message = "firstReading cannot be lower than 0")
    private Float firstReading;

    @NotNull(message = "lastReading cannot be null")
    @Min(value = 0,message = "lastReading cannot be lower than 0")
    private Float lastReading;

    @NotNull(message = "totalConsumptionKWH cannot be null")
    @Min(value = 0,message = "totalConsumptionKWH cannot be lower than 0")
    private Float totalConsumptionKWH;

    @NotNull(message = "initialDate cannot be null")
    private LocalTime initialDate;

    @NotNull(message = "lastDate cannot be null")
    private LocalTime lastDate;

    @ManyToOne
    @NotNull(message = "tariff cannot be null")
    private Tariff tariff;

    @NotNull(message = "totalAmount cannot be null")
    @Min(value = 0,message = "totalAmount cannot be lower than 0")
    private Float totalAmount;


}
