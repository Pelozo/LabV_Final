package net.pelozo.FinalTPLab5DB2.model.projections;

import net.pelozo.FinalTPLab5DB2.model.Address;
import net.pelozo.FinalTPLab5DB2.model.dto.MeterDto;

public interface ResidenceView {
    Long getId();

    MeterDto getMeter();

    Address getAddress();
}
