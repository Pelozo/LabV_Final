package net.pelozo.FinalTPLab5DB2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pelozo.FinalTPLab5DB2.model.MeterModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeterDto{

    private Long id;

    //private MeterModel model;

    private String serialNumber;
}
