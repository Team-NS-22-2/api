package com.mju.insuranceCompany.service.insurance.service.interfaces;

import com.mju.insuranceCompany.service.insurance.controller.dto.UploadAuthFileResultDto;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthFileType;
import org.springframework.web.multipart.MultipartFile;

public interface InsuranceFileService {

    UploadAuthFileResultDto uploadAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType);

    UploadAuthFileResultDto updateAuthFile(int insuranceId, MultipartFile multipartFile, SalesAuthFileType fileType);

    void deleteAuthFile(int insuranceId, SalesAuthFileType fileType);

}
