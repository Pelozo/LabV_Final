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
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"serialNumber"})})
public class Meter {



    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "isDeleted cannot be null")
    @NotEmpty(message = "isDeleted cannot be empty")
    @NotBlank(message = "isDeleted cannot be blank")
    private boolean isDeleted;

    @NotNull(message = "brand cannot be null")
    @NotEmpty(message = "brand cannot be empty")
    @NotBlank(message = "brand cannot be blank")
    private String brand; //maybe make entity

    @NotNull(message = "model cannot be null")
    @NotEmpty(message = "model cannot be empty")
    @NotBlank(message = "model cannot be blank")
    private String model;

    @NotNull(message = "serialNumber cannot be null")
    @NotEmpty(message = "serialNumber cannot be empty")
    @NotBlank(message = "serialNumber cannot be blank")
    //preguntar por final al profe
    private String serialNumber;

}
