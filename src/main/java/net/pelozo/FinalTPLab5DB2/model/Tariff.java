package net.pelozo.FinalTPLab5DB2.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tariffs",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private String name;

    private boolean isDeleted;

    @NotNull(message = "cannot be null")
    private Float value;
}
