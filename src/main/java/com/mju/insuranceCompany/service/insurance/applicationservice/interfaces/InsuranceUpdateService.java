package com.mju.insuranceCompany.service.insurance.applicationservice.interfaces;

import com.mju.insuranceCompany.service.insurance.domain.SalesAuthorizationState;

public interface InsuranceUpdateService {

    void updateSalesAuthorizationState(int insuranceId, SalesAuthorizationState salesAuthorizationState);

}
