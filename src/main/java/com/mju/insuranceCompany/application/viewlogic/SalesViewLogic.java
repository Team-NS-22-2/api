package com.mju.insuranceCompany.application.viewlogic;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import com.mju.insuranceCompany.application.domain.contract.CarType;
import com.mju.insuranceCompany.application.domain.contract.Contract;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.domain.insurance.*;
import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import com.mju.insuranceCompany.application.global.constant.ContractConstants;
import com.mju.insuranceCompany.application.global.exception.InputException;
import com.mju.insuranceCompany.application.global.exception.InputNullDataException;
import com.mju.insuranceCompany.application.global.exception.MyIllegalArgumentException;
import com.mju.insuranceCompany.application.global.exception.NoResultantException;
import com.mju.insuranceCompany.application.global.utility.MenuUtil;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;
import com.mju.insuranceCompany.application.viewlogic.dto.UserDto.UserDto;

import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.CarContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.ContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.FireContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.HealthContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customerDto.CustomerDto;

import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * packageName :  main.domain.viewUtils.viewlogic
 * fileName : SalesViewLogic
 * author :  규현
 * date : 2022-05-10
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-10                규현             최초 생성
 */
public class SalesViewLogic implements ViewLogic {
    private MyBufferedReader br;
    private Employee employee;
    private Insurance insurance;

    public SalesViewLogic(Employee employee) {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.employee = employee;
    }

    @Override
    public String showMenu() {
        return MenuUtil.createMenuAndLogout(ContractConstants.SALES_MENU, ContractConstants.SALES_MENU_ELEMENTS);
    }

    @Override
    public void work(String command) {
        try {
            if (CommonConstants.ONE.equals(command)) {
                selectInsurance();
            }
        } catch (MyIllegalArgumentException | InputNullDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectInsurance() {
        ArrayList<Insurance> insurances = employee.readInsurances();
        if(insurances.size() == 0)
            throw new NoResultantException();
        while (true) {
            System.out.println(ContractConstants.CONTRACT_INSURANCE_LIST);
            System.out.printf(ContractConstants.CONTRACT_INSURANCES_CATEGORY_FORMAT, ContractConstants.CONTRACT_INSURANCE_ID, ContractConstants.CONTRACT_INSURANCE_NAME, ContractConstants.CONTRACT_INSURANCE_TYPE);
            System.out.println(ContractConstants.CONTRACT_SHORT_DIVISION);
            for (Insurance insurance : insurances) {
                if (insurance.getDevelopInfo().getSalesAuthorizationState() == SalesAuthorizationState.PERMISSION)
                    System.out.printf(ContractConstants.CONTRACT_INSURANCES_VALUE_FORMAT, insurance.getId(), insurance.getName(), insurance.printInsuranceType());
            }

            try {
                int insuranceId = 0;
                System.out.println(ContractConstants.SALES_SELECT_INSURANCE_ID);
                insuranceId = (int) br.verifyRead(ContractConstants.CONTRACT_INPUT_INSURANCE_ID, insuranceId);
                if(insuranceId == 0) break;

                insurance = employee.readInsurance(insuranceId);
                if (insurance.getDevelopInfo().getSalesAuthorizationState() == SalesAuthorizationState.PERMISSION) {
                    System.out.println(ContractConstants.CONTRACT_INSURANCE_DESCRIPTION + insurance.getDescription() + ContractConstants.CONTRACT_INSURANCE_CONTRACT_PERIOD + insurance.getContractPeriod() +
                            ContractConstants.CONTRACT_YEAR_PARAMETER + ContractConstants.CONTRACT_INSURANCE_PAYMENT_PERIOD + insurance.getPaymentPeriod() + ContractConstants.CONTRACT_YEAR_PARAMETER + ContractConstants.CONTRACT_INSURANCE_GUARANTEE);
                    System.out.printf(ContractConstants.CONTRACT_GUARANTEES_CATEGORY_FORMAT, "", ContractConstants.CONTRACT_INSURANCE_GUARANTEE_DESCRIPTION, ContractConstants.CONTRACT_INSURANCE_GUARANTEE_AMOUNT);
                    System.out.println(ContractConstants.CONTRACT_LONG_DIVISION);
                    for(Guarantee guarantee : insurance.getGuaranteeList()){
                        System.out.printf(ContractConstants.CONTRACT_GUARANTEES_VALUE_FORMAT, guarantee.getName(), guarantee.getDescription(), guarantee.getGuaranteeAmount());
                    }
                    ProgressContract();
                }
                else {
                    throw new MyIllegalArgumentException(ContractConstants.exceptionNoInsurance(insurance.getId()));
                }
            } catch (InputException | MyIllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void ProgressContract() {
        while (true) {
            int choice = br.verifyCategory(ContractConstants.SALES_INQUIRE_CONDITION, CommonConstants.CATEGORY_YES_OR_NO);
            if (choice == 1)
                inquireCondition();
            else
                break;
        }


        ContractDto contractDto =  switch (insurance.getInsuranceType()) {
            case HEALTH -> planHealthInsurance();
            case FIRE -> planFireInsurance();
            case CAR -> planCarInsurance();
        };

        int choice = br.verifyCategory(ContractConstants.SALES_PROGRESS_CONTRACT, CommonConstants.CATEGORY_YES_OR_NO);
        switch (choice) {
            case 1 -> {
                CustomerDto customerDto = inputCustomerInfo();
                contractDto = switch (insurance.getInsuranceType()) {
                    case HEALTH -> inputHealthInfo(contractDto);
                    case FIRE -> inputFireInfo(contractDto);
                    case CAR -> inputCarInfo(contractDto);
                };
                concludeContract(customerDto, contractDto);
            }
            case 2 -> System.out.println(ContractConstants.SALES_CANCEL);
        }
    }

    private void inquireCondition() {
        System.out.println(ContractConstants.SALES_INSURANCE_DATAIL);
        switch (insurance.getInsuranceType()) {
            case HEALTH -> {
                System.out.printf(ContractConstants.CONTRACT_HEALTH_DETAIL_CATEGORY_FORMAT, ContractConstants.SALES_TARGET_AGE, ContractConstants.SALES_TARGET_SEX, ContractConstants.SALES_RISK_CRITERTION, ContractConstants.SALES__PREMIUM);
                System.out.println(ContractConstants.CONTRACT_LONG_DIVISION);
                for (InsuranceDetail insuranceDetail : insurance.getInsuranceDetailList()) {
                    System.out.printf(ContractConstants.CONTRACT_HEALTH_DETAIL_VALUE_FORMAT, ((HealthDetail) insuranceDetail).printTargetAge(),
                            ((HealthDetail) insuranceDetail).getTargetSex(), ((HealthDetail) insuranceDetail).getRiskCriterion(),
                            insuranceDetail.getPremium());
                }
            }
            case FIRE -> {
                System.out.printf(ContractConstants.CONTRACT_FIRE_DETAIL_CATEGORY_FORMAT, ContractConstants.SALES_BUIILDING_TYPE, ContractConstants.SALES_COLLATERAL_AMOUNT_CRITERION, ContractConstants.SALES__PREMIUM);
                System.out.println(ContractConstants.CONTRACT_SHORT_DIVISION);
                for (InsuranceDetail insuranceDetail : insurance.getInsuranceDetailList()) {
                    System.out.printf(ContractConstants.CONTRACT_FIRE_DETAIL_VALUE_FORMAT, ((FireDetail) insuranceDetail).printBuildingType(), ((FireDetail) insuranceDetail).printCollateralAmountCriterion(), insuranceDetail.getPremium());
                }
            }
            case CAR -> {
                System.out.printf(ContractConstants.CONTRACT_CAR_DETAIL_CATEGORY_FORMAT, ContractConstants.SALES_TARGET_AGE, ContractConstants.SALES_VALUE_CRITERION, ContractConstants.SALES__PREMIUM);
                System.out.println(ContractConstants.CONTRACT_SHORT_DIVISION);
                for (InsuranceDetail insuranceDetail : insurance.getInsuranceDetailList()) {
                    System.out.printf(ContractConstants.CONTRACT_CAR_DETAIL_VALUE_FORMAT, ((CarDetail) insuranceDetail).printTargetAge(),
                            (( CarDetail) insuranceDetail).printValueCriterion(), insuranceDetail.getPremium());
                }
            }
        }
        while (true){
            int choice = br.verifyMenu(ContractConstants.SALES_BACK, CommonConstants.CATEGORY_BACK);
            if (choice == 0)
                break;
        }
    }

    private ContractDto planHealthInsurance() {
        int riskCount = 0, targetAge = 0;
        boolean targetSex, isDrinking, isSmoking, isDriving, isDangerActivity, isTakingDrug, isHavingDisease;

        targetAge = (int) br.verifyRead(ContractConstants.SALES_TARGET_AGE_QUERY, targetAge);
        targetSex =  br.verifyCategory(ContractConstants.CONTRACT_TARGET_SEX_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        isDrinking = br.verifyCategory(ContractConstants.CONTRACT_IS_DRINKING_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if(isDrinking) riskCount++;
        isSmoking = br.verifyCategory(ContractConstants.CONTRACT_IS_SMOKING_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if(isSmoking) riskCount++;
        isDriving = br.verifyCategory(ContractConstants.CONTRACT_IS_DRIVING_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if(isDriving) riskCount++;
        isDangerActivity = br.verifyCategory(ContractConstants.CONTRACT_IS_DANGER_ACTIVITY_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if(isDangerActivity) riskCount++;
        isTakingDrug = br.verifyCategory(ContractConstants.CONTRACT_IS_TAKING_DRUG_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if(isTakingDrug) riskCount++;
        isHavingDisease = br.verifyCategory(ContractConstants.CONTRACT_IS_HAVING_DISEASE_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        if (isHavingDisease) riskCount++;

        int premium = employee.planHealthInsurance(targetAge, targetSex, riskCount, insurance);
        System.out.println(ContractConstants.premiumInquiry(premium));

        HealthContractDto healthContractDto = new HealthContractDto();
        healthContractDto.setDrinking(isDrinking)
                .setSmoking(isSmoking)
                .setDriving(isDriving)
                .setDangerActivity(isDangerActivity)
                .setTakingDrug(isTakingDrug)
                .setHavingDisease(isHavingDisease)
                .setPremium(premium);
        return healthContractDto;
    }

    private ContractDto planFireInsurance() {
        BuildingType buildingType;
        Long collateralAmount = 0L;

        buildingType = switch (br.verifyCategory(ContractConstants.CONTRACT_BUILDING_TYPE_QUERY, CommonConstants.CATEGORY_FOUR)) {
            case 1 -> BuildingType.COMMERCIAL;
            case 2 -> BuildingType.INDUSTRIAL;
            case 3 -> BuildingType.INSTITUTIONAL;
            case 4 -> BuildingType.RESIDENTIAL;
            default -> throw new IllegalStateException();
        };
        collateralAmount = (Long) br.verifyRead(ContractConstants.CONTRACT_COLLATERAL_AMOUNT_QUERY, collateralAmount);

        int premium = employee.planFireInsurance(buildingType, collateralAmount, insurance);
        System.out.println(ContractConstants.premiumInquiry(premium));

        FireContractDto fireContractDto = new FireContractDto();
        fireContractDto.setBuildingType(buildingType)
                .setCollateralAmount(collateralAmount)
                .setPremium(premium);
        return fireContractDto;
    }

    private ContractDto planCarInsurance() {
        int targetAge = 0;
        Long value = 0L;

        targetAge = (int) br.verifyRead(ContractConstants.SALES_TARGET_AGE_QUERY, targetAge);
        value = (Long) br.verifyRead(ContractConstants.CONTRACT_VALUE_QUERY, value);

        int premium = employee.planCarInsurance(targetAge, value, insurance);
        System.out.println(ContractConstants.premiumInquiry(premium));

        CarContractDto carContractDto = new CarContractDto();
        carContractDto.setValue(value)
                    .setPremium(premium);
        return carContractDto;
    }

    private CustomerDto inputCustomerInfo() {
        CustomerDto customerDto = null;

        int choice = br.verifyCategory(ContractConstants.SALES_IS_CONTRACTED_CUSTOMER, 2);
        if (choice == 2) {
            // 미등록 고객
            String name = "", ssn = "", phone = "", address = "", email = "", job = "";
            System.out.println(ContractConstants.CONTRACT_INPUT_CUSTOMER_INFO);

            name = (String) br.verifySpecificRead(ContractConstants.CONTRACT_CUSTOMER_NAME_QUERY, name, "name");
            ssn = (String) br.verifySpecificRead(ContractConstants.CONTRACT_SSN_QUERY, ssn, "ssn");
            phone = (String) br.verifySpecificRead(ContractConstants.CONTRACT_PHONE_QUERY, phone, "phone");
            address = (String) br.verifyRead(ContractConstants.CONTRACT_ADDRESS_QUERY, address);
            email = (String) br.verifySpecificRead(ContractConstants.CONTRACT_EMAIL_QUERY, email, "email");
            job = (String) br.verifyRead(ContractConstants.CONTRACT_JOB_QUERY, job);

            customerDto = new CustomerDto(name, ssn, phone, address, email, job);
        }
        return customerDto;
    }

    private ContractDto inputHealthInfo(ContractDto contractDto) {
        String diseaseDetail = "";
        int height = 0, weight = 0;
        System.out.println(ContractConstants.CONTRACT_INPUT_HEALTH_INFO);

        height = (int) br.verifyRead(ContractConstants.CONTRACT_HEIGHT_QUERY, height);
        weight = (int) br.verifyRead(ContractConstants.CONTRACT_WEGHIT_QUERY, weight);
        if (((HealthContractDto) contractDto).isHavingDisease())
            diseaseDetail = (String) br.verifyRead(ContractConstants.CONTRACT_DISEASE_DETAIL_QUERY, diseaseDetail);

        ((HealthContractDto) contractDto).setHeight(height)
                                        .setWeight(weight)
                                        .setDiseaseDetail(diseaseDetail)
                                        .setEmployeeId(employee.getId());
        return contractDto;
    }

    private ContractDto inputFireInfo(ContractDto contractDto) {
        int buildingArea = 0;
        boolean isSelfOwned, isActualResidence;

        buildingArea = (int) br.verifyRead(ContractConstants.CONTRACT_BUILDING_AREA_QUERY, buildingArea);
        isSelfOwned = br.verifyCategory(ContractConstants.CONTRACT_IS_SELF_OWNED_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        isActualResidence = br.verifyCategory(ContractConstants.CONTRACT_IS_ACTUAL_RESIDENCE_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;

        ((FireContractDto) contractDto).setBuildingArea(buildingArea)
                                        .setSelfOwned(isSelfOwned)
                                        .setActualResidence(isActualResidence)
                                        .setEmployeeId(employee.getId());
        return contractDto;
    }

    private ContractDto inputCarInfo(ContractDto contractDto) {
        CarType carType;
        String modelName = "", carNo = "";
        int modelYear = 0;

        carNo = (String) br.verifySpecificRead(ContractConstants.CONTRACT_CAR_NO_QUERY, carNo, "carNo");
        carType = switch (br.verifyCategory(ContractConstants.CONTRACT_CAR_TYPE_QUERY, CommonConstants.CATEGORY_SEVEN)) {
            case 1 -> CarType.URBAN;
            case 2 -> CarType.SUBCOMPACT;
            case 3 -> CarType.COMPACT;
            case 4 -> CarType.MIDSIZE;
            case 5 -> CarType.LARGESIZE;
            case 6 -> CarType.FULLSIZE;
            case 7 -> CarType.SPORTS;
            default -> throw new IllegalStateException();
        };
        modelName = (String) br.verifyRead(ContractConstants.CONTRACT_MADEL_NAME_QUERY, modelName);
        modelYear = (int) br.verifyRead(ContractConstants.CONTRACT_MODEL_YEAR_QUERY, modelYear);

        ((CarContractDto) contractDto).setCarNo(carNo)
                                    .setCarType(carType)
                                    .setModelName(modelName)
                                    .setModelYear(modelYear)
                                    .setEmployeeId(employee.getId());
        return  contractDto;
    }

    private void concludeContract(CustomerDto customerDto, ContractDto contractDto) {
        int customerId = 0;
        Customer customer;
        int choice = br.verifyCategory(ContractConstants.SALES_CONCLUDE_CONTRACT, CommonConstants.CATEGORY_YES_OR_NO);
        switch (choice) {
            case 1 -> {
                if (customerDto == null) {
                    while (true) {
                        try {
                            customerId = (int) br.verifyRead(ContractConstants.SALES_INPUT_CUSTOMER_ID, customerId);
                            customer = employee.readCustomer(customerId);
                            if (customer != null)
                                break;
                        } catch (MyIllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else {
                    customer = employee.registerCustomer(customerDto);
                    UserDto userDto = signUp(customer.getId());
                    employee.registerUser(userDto);
                }
                Contract contract = employee.registerContract(customer, contractDto, insurance);
                System.out.println(customer);
                System.out.println(contract);
                System.out.println(ContractConstants.SALES_CONCLUDE);
            }
            case 2 -> System.out.println(ContractConstants.SALES_CANCEL);
        }
    }

    private UserDto signUp(int customerId) {
        String userId = "", password = "";

        System.out.println(ContractConstants.CONTRACT_SIGN_UP);
        userId = (String) br.verifyRead(ContractConstants.CONTRACT_USER_ID_QUERY, userId);
        password = (String) br.verifyRead(ContractConstants.CONTRACT_USER_PASSWORD_QUERY, password);
        return new UserDto(userId, password, customerId);
    }
}
