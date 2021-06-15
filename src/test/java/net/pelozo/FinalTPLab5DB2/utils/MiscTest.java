package net.pelozo.FinalTPLab5DB2.utils;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MiscTest {

    /*
    @Test
    public void parseDataConstraintExFKOkTest(){
        final String sqlFk = "could not execute statement; SQL [n/a]; constraint [\"FK_INVOICE_HOME: PUBLIC.INVOICES FOREIGN KEY(RESIDENCE_ID) REFERENCES PUBLIC.RESIDENCES(ID) (1)\"; SQL statement:\n" +
                "update residences set id=null where id=? [23503-200]]";
        String expected = "Constraint error with residence id in invoice";
        DataIntegrityViolationException ex = new DataIntegrityViolationException(sqlFk);

        String response = Misc.parseDataConstraintEx(ex);

        assertEquals(expected,response);
    }

    @Test
    public void parseDataConstraintExUnqOkTest(){
        final String sqlUnique = "could not execute statement; SQL [n/a]; constraint [\"PUBLIC.UNQ_TARIFF_INDEX_C ON PUBLIC.TARIFFS(NAME) VALUES 2\"; SQL statement:\n" +
                "insert into tariffs (id, name, amount) values (null, ?, ?) [23505-200]]";

        String expected = "Constraint error with name in tariff";
        DataIntegrityViolationException ex = new DataIntegrityViolationException(sqlUnique);

        String response = Misc.parseDataConstraintEx(ex);

        assertEquals(expected,response);
    }

    @Test
    public void parseDataConstraintExUndefinedTest(){
        final String sqlUndefiend = "sql error?";
        String expected = "Constraint error";
        DataIntegrityViolationException ex = new DataIntegrityViolationException(sqlUndefiend);

        String response = Misc.parseDataConstraintEx(ex);

        //assertEquals(expected,response);
    }

     */
}
