package net.pelozo.FinalTPLab5DB2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.dto.ClientDto;
import net.pelozo.FinalTPLab5DB2.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TestUtils {

    public static String aClientJSON(){
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(aClient());
    }


    public static Client aClient(){
        Client c = new Client();
        c.setId(1L);
        c.setDni("39170489");
        c.setEmail("martincaminero64@gmail.com");
        c.setFirstName("martin");
        c.setLastName("caminero");
        c.setResidences(List.of());
        c.setPassword("1234");
        c.setUsername("martin64");
        return c;
    }

    public static ClientDto aClientDto(){

        return ClientDto.builder()
                .id(1L)
                .dni("39170489")
                .email("martincaminero64@gmail.com")
                .firstName("martin")
                .lastName("caminero")
                .residences(List.of())
                .username("martin64")
                .build();
    }

    public static Page<Client> aClientPage(){
        return new PageImpl<>(List.of(aClient()));
    }

    public static Pageable aPageable(){
        return PageRequest.of(0,10);
    }

    public static Invoice aInvoice(){
        return Invoice.builder()
                .id(1L)
                .isDue(false)
                .isPaid(false)
                .firstReading(100f)
                .lastReading(150f)
                .build();
    }

    public static Page<Invoice> aInvoicePage(){
        return new PageImpl<>(List.of(aInvoice()));
    }

    public static Page<Object> anEmptyPage(){
        return new PageImpl<>(List.of());
    }

    public static Tariff aTariff(){
        return new Tariff(1L, "name", 20f);
    }

    public static Page<Tariff> aTariffPage(){
        return new PageImpl<>(List.of(aTariff()));
    }


}
