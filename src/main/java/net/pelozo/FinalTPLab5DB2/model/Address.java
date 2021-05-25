package net.pelozo.FinalTPLab5DB2.model;


import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;


//TODO this is a placeholder
@Data
@Entity
@Table(name="addresses")
public class Address {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private int number;


}
