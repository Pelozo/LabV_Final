package net.pelozo.FinalTPLab5DB2.model.TestDeleteLater;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pelozo.FinalTPLab5DB2.model.MeterBrand;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meter_models")
public class MeterModel2 extends BaseModel{

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private MeterBrand brand;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
