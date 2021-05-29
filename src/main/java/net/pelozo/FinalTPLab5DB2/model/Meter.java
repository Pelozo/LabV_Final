package net.pelozo.FinalTPLab5DB2.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meters",uniqueConstraints={@UniqueConstraint(columnNames={"serialNumber"})})

public class Meter {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "model cannot be null")
    @ManyToOne
    @JoinColumn(name = "model_id")
    private MeterModel model;

    @NotNull(message = "serialNumber cannot be null")
    @NotEmpty(message = "serialNumber cannot be empty")
    @NotBlank(message = "serialNumber cannot be blank")
    private String serialNumber;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    private String password;

}

