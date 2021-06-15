package net.pelozo.FinalTPLab5DB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "residences")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @NotNull(message = "client cannot be null")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull(message = "tariff cannot be null")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @NotNull(message = "meter cannot be null")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotNull(message = "address cannot be null")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

}
