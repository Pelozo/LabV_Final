package net.pelozo.FinalTPLab5DB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: a esto hay que ponerle @JsonIgnore
    // TODO: es necesario hacer ResidenceDTO por si queresmos mostrar el dueño de una casa???

    @JsonBackReference
    @NotNull(message = "client cannot be null")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull(message = "tariff cannot be null")
    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @NotNull(message = "meter cannot be null")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotNull(message = "address cannot be null")
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
