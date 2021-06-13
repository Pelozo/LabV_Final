/*
package net.pelozo.FinalTPLab5DB2.controller;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import net.pelozo.FinalTPLab5DB2.model.dto.MeasurementsDto;
import net.pelozo.FinalTPLab5DB2.service.BackofficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final TariffController tariffController;
    private final BackofficeService backofficeService;
    private final MeasurementController measurementController;

    @Autowired
    public BackOfficeController(TariffController tariffController, BackofficeService backofficeService, MeasurementController measurementController) {
        this.tariffController = tariffController;
        this.backofficeService = backofficeService;
        this.measurementController = measurementController;
    }


}
*/