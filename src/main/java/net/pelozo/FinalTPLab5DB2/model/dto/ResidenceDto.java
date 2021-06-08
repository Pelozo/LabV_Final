package net.pelozo.FinalTPLab5DB2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pelozo.FinalTPLab5DB2.model.Address;
import net.pelozo.FinalTPLab5DB2.model.Tariff;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidenceDto {

    private Tariff tariff;

    private MeterDto meter;

    private Address address;
}
