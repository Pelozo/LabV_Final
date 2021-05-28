package net.pelozo.FinalTPLab5DB2.projections;

import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.Tariff;


import java.time.LocalTime;

public interface InvoiceProjection {

    Boolean getIsPaid();

    LocalTime getDueTime();

    Client getClient();

    Residence getResidence();

    float getFirstReading();

    float getLastReading();

    float getTotalConsumptionKWH();

    LocalTime getInitialDate();

    LocalTime getLastDate();

    Tariff getTariff();

    float getTotalAmount();

}
