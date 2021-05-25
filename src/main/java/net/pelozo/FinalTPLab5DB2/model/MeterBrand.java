package net.pelozo.FinalTPLab5DB2.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meter_brands")
public class MeterBrand {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    private String name;

}
