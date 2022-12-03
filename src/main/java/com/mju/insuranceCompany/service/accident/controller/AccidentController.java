package com.mju.insuranceCompany.service.accident.controller;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.AccidentType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acc")
public class AccidentController {

    private final AccidentService accidentService;

    @PostMapping("/report/car-accident")
    public ResponseEntity<CarAccidentDto> reportCarAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportCarAccident(accidentReportDto));
    }

    @PostMapping("/report/car-breakdown")
    public ResponseEntity<CarBreakdownDto> reportCarBreakdown(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportCarBreakdown(accidentReportDto));
    }

    @PostMapping("/report/fire-accident")
    public ResponseEntity<FireAccidentDto> reportFireAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportFireAccident(accidentReportDto));
    }

    @PostMapping("/report/injury-accident")
    public ResponseEntity<InjuryAccidentDto> reportInjuryAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportInjuryAccident(accidentReportDto));
    }

    @GetMapping("/info/list")
    public ResponseEntity<List<AccidentListInfoDto>> getAccidentListOfCustomer() {
        return ResponseEntity.ok(accidentService.getAccidentListOfCustomer());
    }

    @PostMapping("/submit/car-accident/{accidentId}/{docType}")
    public ResponseEntity<Void> submitCarAccidentClaimFile(@PathVariable int accidentId, @PathVariable AccDocType docType, @RequestBody MultipartFile multipartFile) {
        accidentService.submitAccidentDocumentFile(accidentId, docType, multipartFile, AccidentType.CAR_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/claim/{accidentId}")
    public ResponseEntity<CompEmployeeDto> claimCompensation(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentService.claimCompensation(accidentId));
    }

}
