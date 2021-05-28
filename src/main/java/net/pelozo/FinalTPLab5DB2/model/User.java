package net.pelozo.FinalTPLab5DB2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username"})})

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    public enum TYPE{
        CLIENT,
        BACKOFFICE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 5, max = 15, message = "username length must be between 5 and 15 characters")

    private String username;

    //@JsonIgnore//preguntar por @JsonIgnore si esta puesto toma el campo como null
    @NotNull(message = "password cannot be null")
    @Size(min = 5, message = "password must have at least 5 characters")
    private String password;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid!")
    private String email;

    @NotEmpty(message = "first name cannot be empty")
    @NotBlank(message = "first name cannot be blank")
    @NotNull(message = "first name cannot be null")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "last name cannot be empty")
    @NotBlank(message = "last name cannot be blank")
    @NotNull(message = "last name cannot be null")
    @Column(name = "last_name")
    private String lastName;




}
