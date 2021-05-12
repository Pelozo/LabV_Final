package net.pelozo.FinalTPLab5DB2.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients", uniqueConstraints={@UniqueConstraint(columnNames={"dni"})})
public class Client extends User {

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid!")
    private String email;

    @NotNull(message = "dni cannot be null")
    @NotEmpty(message = "dni cannot be empty")
    @NotBlank(message = "dni cannot be blank")
    @Size(min = 8,max = 8,message = "DNI must be exactly 8 (eight) numbers!")
    private String dni;

    @NotEmpty(message = "first name cannot be empty")
    @NotBlank(message = "first name cannot be blank")
    @NotNull(message = "first name cannot be null")
    private String firstName;

    @NotEmpty(message = "last name cannot be empty")
    @NotBlank(message = "last name cannot be blank")
    @NotNull(message = "last name cannot be null")
    private String lastName;

    @NotEmpty(message = "residence cannot be empty")
    @NotBlank(message = "residence cannot be blank")
    @NotNull(message = "residence cannot be null")
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "residence_id")
    private List<Residence> residence;

}
