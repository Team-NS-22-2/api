package com.mju.insuranceCompany.service.insurance.service;

import com.mju.insuranceCompany.global.utility.AuthenticationExtractor;
import com.mju.insuranceCompany.global.utility.S3Client;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import com.mju.insuranceCompany.service.insurance.controller.dto.*;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationFile;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;
    private final S3Client s3Client;

    private Insurance getInsuranceById(int insuranceId) {
        return insuranceRepository.findById(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
    }

    /*
    TODO 이 메소드는 모든 보험정보를 조회하는 메소드다. 하지만 체결을 위한 보험은 판매상태가 PERMISSION 상태여야 한다. 다른 메소드를 만들어서 판매할 때는 해당 메소드를 호출하도록 해야하는가?
     */
    public List<InsuranceListDto> getAllInsuranceListForSale() {
        return insuranceRepository.findAll().stream().map(InsuranceListDto::toDto).toList();
    }
    
    public InsuranceGuaranteeDto getInsuranceGuaranteeById(int insuranceId) {
        Insurance insurance = getInsuranceById(insuranceId);
        return InsuranceGuaranteeDto.toDto(insurance);
    }

    public InsurancePremiumDto inquireHealthPremium(int insuranceId, InquireHealthPremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireHealthPremium(requestDto.getSsn(), requestDto.getRiskCount());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public InsurancePremiumDto inquireFirePremium(int insuranceId, InquireFirePremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireFirePremium(requestDto.getBuildingType(), requestDto.getCollateralAmount());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public InsurancePremiumDto inquireCarPremium(int insuranceId, InquireCarPremiumDto requestDto) {
        Insurance insurance = getInsuranceById(insuranceId);
        int premium = insurance.inquireCarPremium(requestDto.getSsn(), requestDto.getValue());
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public List<InsuranceOfDeveloperDto> getInsuranceListOfDeveloper() {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        return insuranceRepository.findInsuranceOfDeveloperByEmployeeId(employee.getId());
    }

    public InsurancePremiumDto calculateHealthPremium(CalculateHealthPremiumDto calculateHealthPremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateHealthPremiumDto.getStandardPremiumDto();
        HealthDetailDto healthDetailDto = calculateHealthPremiumDto.getHealthDetailDto();
        int premium = Insurance.calcHealthPremium(standardPremiumDto, healthDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public InsurancePremiumDto calculateCarPremium(CalculateCarPremiumDto calculateCarPremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateCarPremiumDto.getStandardPremiumDto();
        CarDetailDto carDetailDto = calculateCarPremiumDto.getCarDetailDto();
        int premium = Insurance.calcCarPremium(standardPremiumDto, carDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public InsurancePremiumDto calculateFirePremium(CalculateFirePremiumDto calculateFirePremiumDto) {
        StandardPremiumDto standardPremiumDto = calculateFirePremiumDto.getStandardPremiumDto();
        FireDetailDto fireDetailDto = calculateFirePremiumDto.getFireDetailDto();
        int premium = Insurance.calcFirePremium(standardPremiumDto, fireDetailDto);
        return InsurancePremiumDto.builder().premium(premium).build();
    }

    public void saveHealthInsurance(SaveHealthInsuranceDto saveHealthInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance healthInsurance = Insurance.createHealthInsurance(
                saveHealthInsuranceDto.getInsuranceBasicInfoDto(),
                saveHealthInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveHealthInsuranceDto.getHealthDetailDtoList()
        );
        insuranceRepository.save(healthInsurance);
    }

    public void saveCarInsurance(SaveCarInsuranceDto saveCarInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance carInsurance = Insurance.createCarInsurance(
                saveCarInsuranceDto.getInsuranceBasicInfoDto(),
                saveCarInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveCarInsuranceDto.getCarDetailDtoList()
        );
        insuranceRepository.save(carInsurance);
    }

    public void saveFireInsurance(SaveFireInsuranceDto saveFireInsuranceDto) {
        int employeeId = AuthenticationExtractor.extractEmployeeIdByAuthentication();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeIdNotFoundException::new);
        Insurance fireInsurance = Insurance.createFireInsurance(
                saveFireInsuranceDto.getInsuranceBasicInfoDto(),
                saveFireInsuranceDto.getGuaranteeDtoList(),
                employee.getId(),
                saveFireInsuranceDto.getFireDetailDtoList()
        );
        insuranceRepository.save(fireInsurance);
    }

    public InsuranceForUploadAuthFileDto getInsuranceInfoForUploadAuthFile(int insuranceId) {
        Insurance insurance = getInsuranceById(insuranceId);
        return InsuranceForUploadAuthFileDto.toDto(insurance);
    }

    public UploadAuthFileResultDto uploadAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        String fileUrl = s3Client.uploadFile(multipartFile);
        SalesAuthorizationFile salesAuthorizationFile = insurance.getSalesAuthorizationFile();
        return getUploadFileResultDto(fileType, salesAuthorizationFile, fileUrl);
    }

    public UploadAuthFileResultDto updateAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        SalesAuthorizationFile salesAuthorizationFile = insurance.getSalesAuthorizationFile();
        String fileUrl = s3Client.updateFile(multipartFile, salesAuthorizationFile.getProdDeclaration());
        return getUploadFileResultDto(fileType, salesAuthorizationFile, fileUrl);
    }

    public void deleteAuthFile(int insuranceId, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        SalesAuthorizationFile salesAuthorizationFile = insurance.getSalesAuthorizationFile();
        switch (fileType) {
            case PROD -> deleteAuthFile(salesAuthorizationFile.deleteProdDeclaration());
            case ISO -> deleteAuthFile(salesAuthorizationFile.deleteIsoVerification());
            case SR_ACTUARY -> deleteAuthFile(salesAuthorizationFile.deleteSrActuaryVerification());
            case FSS_OFFICIAL -> deleteAuthFile(salesAuthorizationFile.deleteFssOfficialDoc());
        }
        insurance.getDevelopInfo().setSalesAuthorizationState(SalesAuthorizationState.DISALLOWANCE);
    }

    private void deleteAuthFile(String fileUrl) {
        s3Client.deleteFile(fileUrl);
    }

    public void updateSalesAuthorizationState(int insuranceId, SalesAuthorizationState salesAuthorizationState) {
        Insurance insurance = getInsuranceById(insuranceId);
        if(salesAuthorizationState == SalesAuthorizationState.WAIT) return;
        insurance.getDevelopInfo().setSalesAuthorizationState(salesAuthorizationState);
    }

    private UploadAuthFileResultDto getUploadFileResultDto(SalesAuthFileType fileType, SalesAuthorizationFile salesAuthorizationFile, String fileUrl) {
        return switch (fileType) {
            case PROD -> getUploadFileResultDto(salesAuthorizationFile.setProdDeclaration(fileUrl));
            case ISO -> getUploadFileResultDto(salesAuthorizationFile.setIsoVerification(fileUrl));
            case SR_ACTUARY -> getUploadFileResultDto(salesAuthorizationFile.setSrActuaryVerification(fileUrl));
            case FSS_OFFICIAL -> getUploadFileResultDto(salesAuthorizationFile.setFssOfficialDoc(fileUrl));
        };
    }

    private UploadAuthFileResultDto getUploadFileResultDto(SalesAuthorizationFile salesAuthorizationFile) {
        return UploadAuthFileResultDto.builder()
                .isExistAllFile(salesAuthorizationFile.isExistAllFile()).build();
    }
}
