package net.pelozo.FinalTPLab5DB2.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Street cannot be null")
    @Column(name = "street")
    private String street;

    @NotNull(message = "Number cannot be null")
    @Column(name = "street_number")
    private int number;


}
