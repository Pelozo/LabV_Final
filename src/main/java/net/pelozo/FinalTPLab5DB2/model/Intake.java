package net.pelozo.FinalTPLab5DB2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intake {
    private float kwhValue;
    private float kwhPrice;
}
