package net.pelozo.FinalTPLab5DB2.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "meters", uniqueConstraints={@UniqueConstraint(columnNames={"serialNumber"})})
public class Meter {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "model cannot be null")
    @ManyToOne
    private MeterModel model;

    @NotNull(message = "serialNumber cannot be null")
    @NotEmpty(message = "serialNumber cannot be empty")
    @NotBlank(message = "serialNumber cannot be blank")
    //preguntar por final al profe
    private String serialNumber;

}

