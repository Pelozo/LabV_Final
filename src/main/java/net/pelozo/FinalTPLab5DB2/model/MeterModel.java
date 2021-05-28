package net.pelozo.FinalTPLab5DB2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meter_models")
public class MeterModel {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;


    @NotNull
    @ManyToOne
    private MeterBrand brand;


}
