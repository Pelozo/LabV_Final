package net.pelozo.FinalTPLab5DB2.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pelozo.FinalTPLab5DB2.model.Invoice;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementsDto {

    private float value;

    private LocalDateTime date;

    private ResidenceDto residenceDto;

    private float kwhPrice;

}
