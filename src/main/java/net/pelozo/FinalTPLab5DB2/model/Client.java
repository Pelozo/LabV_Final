package net.pelozo.FinalTPLab5DB2.model;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "clients", uniqueConstraints={@UniqueConstraint(columnNames={"dni"})})
public class Client extends User {

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "dni cannot be null")
    @NotEmpty(message = "dni cannot be empty")
    @NotBlank(message = "dni cannot be blank")
    private String dni;

    @NotNull(message = "first name cannot be null")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    private String lastName;

    @NotNull(message = "residence cannot be null")
    @OneToMany
    @JoinColumn(name = "residence_id")
    private List<Residence> residence;

}
