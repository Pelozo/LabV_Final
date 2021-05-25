package net.pelozo.FinalTPLab5DB2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    private Long id;

    @NotNull(message = "isPaid cannot be null")
    @Column(name = "is_paid")
    private Boolean isPaid;

    @NotNull(message = "isDue cannot be null")
    @Column(name = "is_due")
    private Boolean isDue;

    @NotNull(message = "dueDate cannot be null")
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @NotNull(message = "residence cannot be null")
    @JoinColumn(name = "residence_id")
    private Residence residence;

    @NotNull(message = "firstReading cannot be null")
    @Min(value = 0,message = "firstReading cannot be lower than 0")
    @Column(name = "first_reading")
    private Float firstReading;

    @NotNull(message = "lastReading cannot be null")
    @Min(value = 0,message = "lastReading cannot be lower than 0")
    @Column(name = "last_reading")
    private Float lastReading;

    @NotNull(message = "totalConsumptionKWH cannot be null")
    @Min(value = 0,message = "totalConsumptionKWH cannot be lower than 0")
    @Column(name = "kwh_measurement")
    private Float totalConsumptionKWH;

    @NotNull(message = "totalAmount cannot be null")
    @Min(value = 0,message = "totalAmount cannot be lower than 0")
    @Column(name = "total")
    private Float totalAmount;



    //aca consigo el total de consumo que tuvo el usuario
    public float getTotalConsumption(){
        return lastReading - firstReading;
    }



}
