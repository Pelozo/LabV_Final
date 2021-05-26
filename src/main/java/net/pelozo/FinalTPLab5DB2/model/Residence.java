package net.pelozo.FinalTPLab5DB2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue
    private Long id;

    // TODO: a esto hay que ponerle @JsonIgnore
    // TODO: es necesario hacer ResidenceDTO por si queresmos mostrar el dueño de una casa???
    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @NotNull
    @OneToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotNull
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
