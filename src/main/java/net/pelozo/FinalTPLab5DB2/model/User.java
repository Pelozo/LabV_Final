package net.pelozo.FinalTPLab5DB2.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;


@Data
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 5, max = 15, message = "username length must be between 5 and 15 characters")
    private String username;


    @NotNull(message = "password cannot be null")
    @Size(min = 5, message = "password must have at least 5 characters")
    private String password;

}
