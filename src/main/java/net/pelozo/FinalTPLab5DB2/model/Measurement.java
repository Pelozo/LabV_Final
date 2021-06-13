package net.pelozo.FinalTPLab5DB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "kwh_value")
    @NotNull(message = "khwValue cannot be null")
    private float kwhValue;

    @NotNull(message = "Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Column(name = "kwh_price")
    private float kwhPrice;

    @NotNull(message = "Residence cannot be null")
    @ManyToOne
    @JoinColumn(name = "residence_id")
    private Residence residence;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


}
