package com.mju.insuranceCompany.service.accident.controller;

import com.mju.insuranceCompany.service.accident.controller.dto.*;
import com.mju.insuranceCompany.service.accident.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acc")
public class AccidentController {

    private final AccidentService accidentService;

    @PostMapping("/car-accident")
    public ResponseEntity<CarAccidentDto> reportCarAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportCarAccident(accidentReportDto));
    }

    @PostMapping("/car-breakdown")
    public ResponseEntity<CarBreakdownDto> reportCarBreakdown(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportCarBreakdown(accidentReportDto));
    }

    @PostMapping("/fire-accident")
    public ResponseEntity<FireAccidentDto> reportFireAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportFireAccident(accidentReportDto));
    }

    @PostMapping("/injury-accident")
    public ResponseEntity<InjuryAccidentDto> reportInjuryAccident(@RequestBody AccidentReportDto accidentReportDto) {
        return ResponseEntity.ok(accidentService.reportInjuryAccident(accidentReportDto));
    }

}
