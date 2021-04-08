package net.pelozo.FinalTPLab5DB2.model;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"serialNumber"})})
public class Meter {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String brand; //maybe make entity

    @NotNull
    private String model;

    @NotNull
    private String serialNumber;
}
