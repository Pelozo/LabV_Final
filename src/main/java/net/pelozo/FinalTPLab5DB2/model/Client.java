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


    @NotNull(message = "dni cannot be null")
    @NotEmpty(message = "dni cannot be empty")
    @NotBlank(message = "dni cannot be blank")
    @Size(min = 8,max = 8,message = "DNI must be exactly 8 (eight) numbers!")
    private String dni;



    @NotNull(message = "residence cannot be null")
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "client")
    private List<Residence> residence;


}
