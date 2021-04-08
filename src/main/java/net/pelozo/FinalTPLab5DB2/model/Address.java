package net.pelozo.FinalTPLab5DB2.model;


import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



//TODO this is a placeholder
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue
    private int id;

    private String street;

    private int number;


}
