package net.pelozo.FinalTPLab5DB2.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Residence {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @NotNull
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
