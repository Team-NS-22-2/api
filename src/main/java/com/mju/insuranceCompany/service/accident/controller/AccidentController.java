package com.mju.insuranceCompany.service.accident.controller;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.domain.AccidentType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentCreateService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentFileService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentReadService;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentUpdateService;
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

    private final AccidentCreateService accidentCreateService;
    private final AccidentReadService accidentReadService;
    private final AccidentUpdateService accidentUpdateService;
    private final AccidentFileService accidentFileService;

    @PostMapping("/report/car-accident")
    public ResponseEntity<CarAccidentDto> reportCarAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentCreateService.reportCarAccident(accidentReportDto));
    }

    @PostMapping("/report/car-breakdown")
    public ResponseEntity<CarBreakdownDto> reportCarBreakdown(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentCreateService.reportCarBreakdown(accidentReportDto));
    }

    @PostMapping("/report/fire-accident")
    public ResponseEntity<FireAccidentDto> reportFireAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentCreateService.reportFireAccident(accidentReportDto));
    }

    @PostMapping("/report/injury-accident")
    public ResponseEntity<InjuryAccidentDto> reportInjuryAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentCreateService.reportInjuryAccident(accidentReportDto));
    }

    @PostMapping("/submit/car-accident/{accidentId}/{docType}")
    public ResponseEntity<Void> submitCarAccidentDocumentFileByCustomer(@PathVariable int accidentId, @PathVariable AccDocType docType, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCustomer(accidentId, docType, multipartFile, AccidentType.CAR_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/submit/fire-accident/{accidentId}/{docType}")
    public ResponseEntity<Void> submitFireAccidentDocumentFileByCustomer(@PathVariable int accidentId, @PathVariable AccDocType docType, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCustomer(accidentId, docType, multipartFile, AccidentType.FIRE_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/submit/injury-accident/{accidentId}/{docType}")
    public ResponseEntity<Void> submitInjuryAccidentDocumentFileByCustomer(@PathVariable int accidentId, @PathVariable AccDocType docType, @RequestBody MultipartFile multipartFile) {
        accidentFileService.submitAccDocFileByCustomer(accidentId, docType, multipartFile, AccidentType.INJURY_ACCIDENT);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/claim/{accidentId}")
    public ResponseEntity<CompEmployeeDto> claimCompensation(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentUpdateService.claimCompensation(accidentId));
    }

    @GetMapping("/info/list")
    public ResponseEntity<List<AccidentListInfoDto>> getAccidentListOfCustomer() {
        return ResponseEntity.ok(accidentReadService.getAccidentListOfCustomer());
    }

    @GetMapping("/car-accident/{accidentId}")
    public ResponseEntity<CarAccidentDto> getCarAccident(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getCarAccidentOfCustomer(accidentId));
    }

    @GetMapping("/car-breakdown/{accidentId}")
    public ResponseEntity<CarBreakdownDto> getCarBreakdown(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getCarBreakdownOfCustomer(accidentId));
    }

    @GetMapping("/fire-accident/{accidentId}")
    public ResponseEntity<FireAccidentDto> getFireAccident(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getFireAccidentOfCustomer(accidentId));
    }

    @GetMapping("/injury-accident/{accidentId}")
    public ResponseEntity<InjuryAccidentDto> getInjuryAccident(@PathVariable int accidentId) {
        return ResponseEntity.ok(accidentReadService.getInjuryAccidentOfCustomer(accidentId));
    }

    @PatchMapping("/change-comp-employee/{accidentId}")
    public ResponseEntity<CompEmployeeDto> changeCompEmployee(@PathVariable int accidentId, @RequestBody ComplainRequestDto dto) {
        return ResponseEntity.ok(accidentUpdateService.changeCompEmployee(accidentId, dto));
    }
}
