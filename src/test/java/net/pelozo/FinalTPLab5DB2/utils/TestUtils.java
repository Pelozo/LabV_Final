package net.pelozo.FinalTPLab5DB2.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.Tariff;
import net.pelozo.FinalTPLab5DB2.model.*;
import net.pelozo.FinalTPLab5DB2.model.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.pelozo.FinalTPLab5DB2.utils.Constants.JWT_SECRET;

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
                //.residences(List.of())
                .username("martin64")
                .build();
    }

    public static Page<Client> aClientPage(){
        return new PageImpl<>(List.of(aClient()));
    }

    public static Pageable aPageable(){
        return PageRequest.of(0,10);
    }

    public static Page<Invoice> anInvoicePage(){
        return new PageImpl<>(List.of(anInvoice()));
    }

    public static Page<Tariff> aTariffPage(){
        return new PageImpl<>(List.of(aTariff()));
    }

    public static MeterBrand aMeterBrand(){
        return new MeterBrand(1L,"meteoro");
    }

    public static MeterModel aMeterModel(){
        return new MeterModel(1L,"metersila",aMeterBrand());
    }

    public static Meter aMeter(){
        return new Meter(1L,aMeterModel(),"123asd","asdf1234");
    }

    public static Tariff aTariff(){
        return new Tariff(1L,"voletaso",12.5f);
    }

    public static Address anAddress(){
        return new Address(1L,"calle falsa",55555);
    }

    public static Residence aResidence(){
        return new Residence(1L,aClient(),aTariff(),aMeter(),anAddress());
    }

    public static Invoice anInvoice(){
        return new Invoice(1L,
                true,
                false,
                LocalDate.now(),
                LocalDate.of(2021,7,21),
                aResidence(),
                100.2f,
                125.6f,
                25.4f,
                1570f);
    }

    public static Measurement aMeasurement(){
        return new Measurement(1L,
                120.5f,
                LocalDateTime.now(),
                12.5f,
                aResidence(),
                anInvoice());
    }

    public static MeasurementDto aMeasurementDto(){
        return new MeasurementDto("123asd",100.6f,LocalDateTime.now(),"asdf1234");
    }

    public static MeasurementsDto aMeasurementsDto(){
        return new ModelMapper().map(aMeasurement(),MeasurementsDto.class);
    }

    public static Page<Measurement> aMeasurementPage() {
        return new PageImpl<>(List.of(aMeasurement()));
    }

    public static Page<MeasurementsDto> aMeasurementsDtoPage(){
        return new PageImpl<>(List.of(aMeasurementsDto()));
    }


    public static Backoffice aBackOffice() {
        Backoffice backoffice = new Backoffice();
        backoffice.setUsername("admin");
        backoffice.setPassword("admin");
        return backoffice;
    }

    public static UserDto aUserDtoFromBackOffice(){
        return new ModelMapper().map(aBackOffice(),UserDto.class);
    }

    public static UserDto aUserDto() {
        return new ModelMapper().map(aClient(),UserDto.class);
    }


    public static LoginRequestDto aLoginRequestDto() {
        return new LoginRequestDto("admin", "'or '1'='1");
    }


    public static Page<Residence> aResidencePage() {
        return new PageImpl<>(List.of(aResidence()));
    }

    public static ResidenceDto aResidenceDto(){
        return new ModelMapper().map(aResidence(),ResidenceDto.class);
    }

    public static MeterDto aMeterDto(){
        return MeterDto.builder().serialNumber("123asd").id(1L).build();
    }

    public static Page<Meter> aMeterPage(){
        return new PageImpl<>(List.of(aMeter()));
    }

    public static Page<MeterDto> aMeterDtoPage(){
        return new PageImpl<>(List.of(aMeterDto()));
    }

}

