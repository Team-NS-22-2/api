package com.mju.insuranceCompany.service.accident.applicationservice.implement;

import com.mju.insuranceCompany.global.constant.StringConstant;
import com.mju.insuranceCompany.global.utility.S3Client;
import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.domain.AccidentType;
import com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.service.accident.exception.AccidentIdNotFoundException;
import com.mju.insuranceCompany.service.accident.exception.CannotClaimCarBreakdownException;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.accident.applicationservice.interfaces.AccidentFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AccidentFileServiceImpl implements AccidentFileService {

    private final S3Client s3Client;
    private final AccidentRepository accidentRepository;

    @Override
    public void submitAccDocFileByCustomer(int accidentId, AccDocType docType, MultipartFile multipartFile, AccidentType accidentType) {
        Accident accident = validateClientAndAccidentType(accidentId, accidentType);
        String fileUrl = s3Client.uploadFile(StringConstant.S3_ACC_DOC_DIRECTORY, multipartFile); // 파일 저장
        accident.addAccidentDocumentFile(docType, fileUrl); // accident's accident document file 추가
    }

    @Override
    public void submitAccDocFileByCompEmployee(int accidentId, MultipartFile multipartFile, AccDocType docType) {
        Accident accident = this.getAccidentById(accidentId);
        accident.validateCompEmployee();
        String fileUrl = s3Client.uploadFile(StringConstant.S3_ACC_DOC_DIRECTORY, multipartFile);
        accident.addAccidentDocumentFile(docType, fileUrl);
    }

    /** Read accident from repository */
    private Accident getAccidentById(int accidentId) {
        return accidentRepository.findById(accidentId).orElseThrow(AccidentIdNotFoundException::new);
    }

    /**
     * 보상금 청구를 위해 자동차/화재/상해 사고에 대한 타입 검증(자동차 고장은 보상금 청구를 할 수 없음)과
     * 해당 사고가 고객의 사고가 맞는지에 대한 검증을 수행하는 메소드.
     */
    private Accident validateClientAndAccidentType(int accidentId, AccidentType accidentType) {
        if(accidentType == AccidentType.CAR_BREAKDOWN) {
            throw new CannotClaimCarBreakdownException();
        }
        Accident accident = this.getAccidentById(accidentId);
        accident.validateClient();
        accident.validateAccidentType(accidentType);
        return accident;
    }
}
