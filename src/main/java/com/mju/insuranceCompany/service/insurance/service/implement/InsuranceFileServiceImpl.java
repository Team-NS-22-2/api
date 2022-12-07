package com.mju.insuranceCompany.service.insurance.service.implement;

import com.mju.insuranceCompany.global.constant.StringConstant;
import com.mju.insuranceCompany.global.utility.S3Client;
import com.mju.insuranceCompany.service.insurance.controller.dto.UploadAuthFileResultDto;
import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.service.interfaces.InsuranceFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuranceFileServiceImpl implements InsuranceFileService {

    private final InsuranceRepository insuranceRepository;
    private final S3Client s3Client;


    public UploadAuthFileResultDto uploadAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        String fileUrl = s3Client.uploadFile(StringConstant.S3_SALES_AUTH_DIRECTORY, multipartFile);
        return insurance.uploadSalesAuthFile(fileType, fileUrl);
    }

    public UploadAuthFileResultDto updateAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        String originFileUrl = insurance.getOriginSalesAuthorizationFileUrl(fileType);
        String updateFileUrl = s3Client.updateFile(StringConstant.S3_SALES_AUTH_DIRECTORY, multipartFile, originFileUrl);
        return insurance.uploadSalesAuthFile(fileType, updateFileUrl);
    }

    public void deleteAuthFile(int insuranceId, SalesAuthFileType fileType) {
        Insurance insurance = getInsuranceById(insuranceId);
        String deleteFileUrl = insurance.deleteSalesAuthFile(fileType);
        s3Client.deleteFile(StringConstant.S3_SALES_AUTH_DIRECTORY, deleteFileUrl);
    }

    private Insurance getInsuranceById(int insuranceId) {
        return insuranceRepository.findById(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
    }

}
