package com.mju.insuranceCompany.service.insurance.service.implement;

import com.mju.insuranceCompany.service.insurance.domain.Insurance;
import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;
import com.mju.insuranceCompany.service.insurance.exception.InsuranceIdNotFoundException;
import com.mju.insuranceCompany.service.insurance.repository.InsuranceRepository;
import com.mju.insuranceCompany.service.insurance.service.interfaces.InsuranceUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuranceUpdateServiceImpl implements InsuranceUpdateService {

    private final InsuranceRepository insuranceRepository;

    public void updateSalesAuthorizationState(int insuranceId, SalesAuthorizationState salesAuthorizationState) {
        Insurance insurance = getInsuranceById(insuranceId);
        insurance.updateSalesAuthorizationState(salesAuthorizationState);
    }

    private Insurance getInsuranceById(int insuranceId) {
        return insuranceRepository.findById(insuranceId).orElseThrow(InsuranceIdNotFoundException::new);
    }

}
