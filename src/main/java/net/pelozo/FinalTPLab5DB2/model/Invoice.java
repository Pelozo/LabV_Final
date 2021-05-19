package net.pelozo.FinalTPLab5DB2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Invoice {

    @Id
    private Long id;

    @NotNull(message = "isPaid cannot be null")
    private Boolean isPaid;

    @NotNull(message = "isDue cannot be null")
    private Boolean isDue;

    @NotNull(message = "dueDate cannot be null")
    private LocalDateTime dueDate;

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

    @NotNull(message = "totalAmount cannot be null")
    @Min(value = 0,message = "totalAmount cannot be lower than 0")
    private Float totalAmount;


    //aca consigo el total de consumo que tuvo el usuario
    public float getTotalConsumption(){
        return lastReading - firstReading;
    }

}
