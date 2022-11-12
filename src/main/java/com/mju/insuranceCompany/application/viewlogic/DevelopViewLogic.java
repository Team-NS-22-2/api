package com.mju.insuranceCompany.application.viewlogic;

import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import com.mju.insuranceCompany.application.domain.insurance.SalesAuthorizationState;
import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import com.mju.insuranceCompany.application.global.constant.DevelopViewLogicConstants;
import com.mju.insuranceCompany.application.global.exception.InputException;
import com.mju.insuranceCompany.application.global.exception.MyFileNotFoundException;
import com.mju.insuranceCompany.application.global.exception.MyIOException;
import com.mju.insuranceCompany.application.global.exception.MyIllegalArgumentException;
import com.mju.insuranceCompany.application.global.utility.CriterionSetUtil;
import com.mju.insuranceCompany.application.global.utility.MenuUtil;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;
import com.mju.insuranceCompany.application.viewlogic.dto.insuranceDto.*;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * packageName :  main.domain.viewUtils.viewlogic
 * fileName : DevViewLogic
 * author :  규현
 * date : 2022-05-10
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-10                규현             최초 생성
 */
public class DevelopViewLogic implements ViewLogic {

    private Employee employee;

    private MyBufferedReader br;

    public DevelopViewLogic(Employee employee) {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.employee = employee;
    }

    @Override
    public String showMenu() {
        return MenuUtil.createMenuAndLogout(DevelopViewLogicConstants.MENU_TITLE_DEV_VIEW_LOGIC, DevelopViewLogicConstants.MENU_ELEMENTS_DEV_VIEW_LOGIC);
    }

    @Override
    public void work(String command) {
        try {
            switch (command){
                case CommonConstants.ONE -> {
                    showInsuranceByEmployee();
                    this.menuDevelop(this.menuInsuranceType());
                }
                case CommonConstants.TWO -> this.menuSalesAuthFile(showInsuranceByEmployeeAndSelect());
            }
        }
        catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        catch (IOException ex) {
            throw new MyIOException();
        }
    }

    private ArrayList<Insurance> showInsuranceByEmployee() {
        System.out.printf(DevelopViewLogicConstants.EMPLOYEE_INSURANCE_LIST, employee.getName());
        ArrayList<Insurance> insuranceArrayList = employee.readMyInsuranceList();
        if(insuranceArrayList.size() == 0) {
            System.out.println(DevelopViewLogicConstants.NONE_INSURANCE_LIST);
        }
        else{
            for(Insurance insurance : insuranceArrayList){
                System.out.println(insurance.printOnlyInsurance());
            }
            System.out.println(CommonConstants.LIST_LINE);
        }
        return insuranceArrayList;
    }

    private Insurance showInsuranceByEmployeeAndSelect()  {
        while (true) {
            try {
                ArrayList<Insurance> insuranceArrayList = showInsuranceByEmployee();
                if(insuranceArrayList.size() == 0) {
                    System.out.println(DevelopViewLogicConstants.ERROR_NONE_INSURANCE_LIST);
                    return null;
                }
                System.out.println(DevelopViewLogicConstants.MENU_SELECT_INSURANCE_FOR_REGISTER_FILE);

                int insuranceId = 0;
                insuranceId = (int) br.verifyRead(DevelopViewLogicConstants.QUERY_INSURANCE_ID, insuranceId);
                if(insuranceId == 0) return null;
                Insurance insurance = employee.readMyInsurance(insuranceId);
                System.out.println(insurance.printOnlyInsurance());
                return insurance;
            } catch (MyIllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private InsuranceType menuInsuranceType() {
        return switch (br.verifyMenu(MenuUtil.createMenuAndClose(DevelopViewLogicConstants.MENU_INSURANCE_TYPE_TITLE, DevelopViewLogicConstants.MENU_INSURANCE_TYPE_ELEMENTS), DevelopViewLogicConstants.MENU_INSURANCE_TYPE_ELEMENTS.length)){
            case 1 -> InsuranceType.HEALTH;
            case 2 -> InsuranceType.CAR;
            case 3 -> InsuranceType.FIRE;
            default -> null;
        };
    }

    private void menuDevelop(InsuranceType type) {
        if (type == null) return;

        DtoBasicInfo dtoBasicInfo = formInputBasicInfo();
        ArrayList<DtoGuarantee> dtoGuarantees = formInputGuaranteeInfo();
        int stPremium = formCalculatePremium(isCalcPremium());
        if(stPremium < 0) return;

        ArrayList<DtoTypeInfo> dtoTypeInfo = formInputTypeInfo(type, stPremium);

        formRegisterInsurance(type, dtoBasicInfo, dtoGuarantees,  dtoTypeInfo);
    }

    private DtoBasicInfo formInputBasicInfo() {
        String name = "", description = "";
        int paymentPeriod = 0, contractPeriod = 0;
        System.out.println(DevelopViewLogicConstants.TITLE_INPUT_BASIC_INFO + DevelopViewLogicConstants.EXIT_SYSTEM);
        name = (String) br.verifyRead(DevelopViewLogicConstants.QUERY_INSURANCE_NAME, name);
        description = (String) br.verifyRead(DevelopViewLogicConstants.QUERY_INSURANCE_DESCRIPTION, description);
        contractPeriod = (int) br.verifyRead(DevelopViewLogicConstants.QUERY_INSURANCE_CONTRACT_PERIOD, contractPeriod);
        paymentPeriod = (int) br.verifyRead(DevelopViewLogicConstants.QUERY_INSURANCE_PAYMENT_PERIOD, paymentPeriod);
        return new DtoBasicInfo(name, description, paymentPeriod, contractPeriod);
    }

    private ArrayList<DtoTypeInfo> formInputTypeInfo(InsuranceType type, int stPremium) {
        return switch (type) {
            case HEALTH -> formDtoHealth(stPremium);
            case CAR -> formDtoCar(stPremium);
            case FIRE -> formDtoFire(stPremium);
            default -> null;
        };
    }

    private ArrayList<DtoTypeInfo> formDtoHealth(int stPremium) {
        boolean isAddHealth = true;
        ArrayList<DtoTypeInfo> healthListInfo = new ArrayList<>();
        while(isAddHealth){
            int targetAge = 0, premium = -1, riskCount = -1;
            boolean targetSex, riskCriterion;
            System.out.println(DevelopViewLogicConstants.TITLE_INPUT_HEALTH_INFO + DevelopViewLogicConstants.EXIT_SYSTEM);
            targetAge = CriterionSetUtil.setTargetAge((int) br.verifyRead(DevelopViewLogicConstants.QUERY_TARGET_AGE, targetAge));
            targetSex = br.verifyCategory(DevelopViewLogicConstants.QUERY_TARGET_SEX, CommonConstants.CATEGORY_YES_OR_NO) == 1;
            while(true) {
                riskCount = (int) br.verifyRead(DevelopViewLogicConstants.QUERY_RISK_CRITERION, riskCount);
                if(riskCount > 6){
                    System.out.println(DevelopViewLogicConstants.ERROR_RISK_CRITERION_COUNT);
                }
                else {
                    riskCriterion = CriterionSetUtil.setRiskCriterion(riskCount);
                    break;
                }
            }
            DtoHealth dtoHealth = new DtoHealth(targetAge, targetSex, riskCriterion);

            premium = employee.calcSpecificPremium(stPremium, dtoHealth);
            System.out.printf(DevelopViewLogicConstants.PRINT_PREMIUM, premium);
            healthListInfo.add(dtoHealth.setPremium(premium));

            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_ADD_HEALTH_INFO, CommonConstants.CATEGORY_YES_OR_NO)){
                case 2 -> isAddHealth = false;
            }
        }
        return healthListInfo;
    }

    private ArrayList<DtoTypeInfo> formDtoCar(int stPremium) {
        boolean isAddCar = true;
        ArrayList<DtoTypeInfo> carListInfo = new ArrayList<>();
        while(isAddCar) {
            int targetAge = 0, premium = -1;
            long valueCriterion = -1;
            System.out.println(DevelopViewLogicConstants.TITLE_INPUT_CAR_INFO + DevelopViewLogicConstants.EXIT_SYSTEM);
            targetAge = CriterionSetUtil.setTargetAge((int) br.verifyRead(DevelopViewLogicConstants.QUERY_TARGET_AGE, targetAge));
            valueCriterion = CriterionSetUtil.setValueCriterion((long) br.verifyRead(DevelopViewLogicConstants.QUERY_VALUE_CRITERION, valueCriterion));
            DtoCar dtoCar = new DtoCar(targetAge, valueCriterion);

            premium = employee.calcSpecificPremium(stPremium, dtoCar);
            System.out.printf(DevelopViewLogicConstants.PRINT_PREMIUM, premium);
            carListInfo.add(dtoCar.setPremium(premium));

            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_ADD_CAR_INFO, CommonConstants.CATEGORY_YES_OR_NO)){
                case 2 -> isAddCar = false;
            }
        }
        return carListInfo;
    }

    private ArrayList<DtoTypeInfo> formDtoFire(int stPremium) {
        boolean isAddFire = true;
        ArrayList<DtoTypeInfo> fireListInfo = new ArrayList<>();
        while(isAddFire){
            BuildingType buildingType = null;
            long collateralAmount = -1;
            int premium = -1;
            System.out.println(DevelopViewLogicConstants.TITLE_INPUT_FIRE_INFO + DevelopViewLogicConstants.EXIT_SYSTEM);
            System.out.println();
            switch (br.verifyCategory(DevelopViewLogicConstants.QUERY_TARGET_BUILDING_TYPE, CommonConstants.CATEGORY_FOUR)){
                case 1 -> buildingType = BuildingType.COMMERCIAL;
                case 2 -> buildingType = BuildingType.INDUSTRIAL;
                case 3 -> buildingType = BuildingType.INSTITUTIONAL;
                case 4 -> buildingType = BuildingType.RESIDENTIAL;
            }
            collateralAmount = CriterionSetUtil.setCollateralAmountCriterion ((long) br.verifyRead(DevelopViewLogicConstants.QUERY_COLLATERAL_AMOUNT, collateralAmount));
            DtoFire dtoFire = new DtoFire(buildingType, collateralAmount);

            premium = employee.calcSpecificPremium(stPremium, dtoFire);
            System.out.printf(DevelopViewLogicConstants.PRINT_PREMIUM, premium);
            fireListInfo.add(dtoFire.setPremium(premium));

            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_ADD_FIRE_INFO, CommonConstants.CATEGORY_YES_OR_NO)){
                case 2 -> isAddFire = false;
            }
        }
        return fireListInfo;
    }

    private ArrayList<DtoGuarantee> formInputGuaranteeInfo() {
        boolean isAddGuarantee = true;
        ArrayList<DtoGuarantee> guaranteeListInfo = new ArrayList<>();
        while(isAddGuarantee) {
            String gName = "", gDescription = "";
            long gAmount = 0;
            System.out.println(DevelopViewLogicConstants.TITLE_INPUT_GUARANTEE + DevelopViewLogicConstants.EXIT_SYSTEM);
            gName = (String) br.verifyRead(DevelopViewLogicConstants.QUERY_GUARANTEE_NAME, gName);
            gDescription = (String) br.verifyRead(DevelopViewLogicConstants.QUERY_GUARANTEE_DESCRIPTION, gDescription);
            gAmount = (Long) br.verifyRead(DevelopViewLogicConstants.QUERY_GUARANTEE_AMOUNT, gAmount);

            guaranteeListInfo.add(new DtoGuarantee(gName, gDescription, gAmount));
            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_ADD_GUARANTEE, CommonConstants.CATEGORY_YES_OR_NO)){
                case 2 -> isAddGuarantee = false;
            }
        }
        return guaranteeListInfo;
    }

    private boolean isCalcPremium() {
        boolean isCalcPremium = false;
        boolean forWhile = true;
        while(forWhile) {
            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_IS_CALCULATE_PREMIUM, CommonConstants.CATEGORY_YES_OR_NO)) {
                case 1 -> {
                    isCalcPremium = true;
                    forWhile = false;
                }
                case 2 -> {
                    switch (br.verifyCategory(DevelopViewLogicConstants.MENU_IS_CANCEL, CommonConstants.CATEGORY_YES_OR_NO)) {
                        case 1 -> forWhile = false;
                    }
                }
            }
        }
        return isCalcPremium;
    }

    private int formCalculatePremium(boolean isCalcPremium) {
        if(!isCalcPremium) return -1;
        return calcStandardPremium();
    }

    private int calcStandardPremium() {
        boolean forWhile = true;
        int stPremium= -1;
        while(forWhile) {
            try {
                long damageAmount = 0, countContract = 0, businessExpense = 0;
                double profitMargin = 0;
                System.out.println(DevelopViewLogicConstants.TITLE_CALC_ST_PREMIUM + DevelopViewLogicConstants.EXIT_SYSTEM);
                damageAmount = (long) br.verifyRead(DevelopViewLogicConstants.QUERY_DAMAGE_AMOUNT, damageAmount);
                countContract = (long) br.verifyRead(DevelopViewLogicConstants.QUERY_COUNT_CONTRACT, countContract);
                businessExpense = (long) br.verifyRead(DevelopViewLogicConstants.QUERY_BIZ_EXPENSE, businessExpense);
                profitMargin = (Double) br.verifyRead(DevelopViewLogicConstants.QUERY_PROFIT_MARGIN, profitMargin);
                stPremium = employee.calcStandardPremium(damageAmount, countContract, businessExpense, profitMargin);
                System.out.printf(DevelopViewLogicConstants.PRINT_ST_PREMIUM, stPremium);
                switch (br.verifyCategory(DevelopViewLogicConstants.MENU_IS_DECISION_ST_PREMIUM, CommonConstants.CATEGORY_YES_OR_NO)){
                    case 1 -> forWhile = false;
                }
            }
            catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
        return stPremium;
    }

    private void formRegisterInsurance(InsuranceType type, DtoBasicInfo dtoBasicInfo, ArrayList<DtoGuarantee> dtoGuaranteeList, ArrayList<DtoTypeInfo> dtoTypeInfoList) {
        boolean forWhile = true;
        while(forWhile){
            switch (br.verifyCategory(DevelopViewLogicConstants.MENU_IS_REGISTER_INSURANCE, CommonConstants.CATEGORY_YES_OR_NO)){
                case 1 -> {
                    employee.develop(type, dtoBasicInfo, dtoGuaranteeList, dtoTypeInfoList);
                    System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_REGISTER_INSURANCE);
                    forWhile = false;
                }
                case 2 -> {
                    switch (br.verifyCategory(DevelopViewLogicConstants.MENU_IS_CANCEL, CommonConstants.CATEGORY_YES_OR_NO)){
                        case 1 -> forWhile = false;
                    }
                }
            }
        }
    }

    private void menuSalesAuthFile(Insurance insurance) throws IOException {
        if(insurance==null) return;
        loop : while(true) {
            try {
                switch (br.verifyMenu(MenuUtil.createMenuAndClose(DevelopViewLogicConstants.TITLE_REGISTER_SALES_AUTH_FILE, DevelopViewLogicConstants.MENU_SALES_AUTH_FILE_TYPE), DevelopViewLogicConstants.MENU_SALES_AUTH_FILE_TYPE.length)){
                    // 보험상품신고서
                    case 1 -> {
                        switch (employee.registerAuthProdDeclaration(insurance)) {
                            // 파일 업로드 성공
                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                            // 파일 업로드 취소
                            case -1 -> { continue; }
                            // 파일 업로드 변경
                            case 0 -> {
                                switch (br.verifyCategory(DevelopViewLogicConstants.ERROR_EXIST_FILE_IS_CHANGE_FILE, CommonConstants.CATEGORY_YES_OR_NO)){
                                    case 1 -> {
                                        switch (employee.registerAuthProdDeclaration(insurance, null)) {
                                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                                            case -1 -> { continue; }
                                        }
                                    }
                                }
                            }
                            // 판매상태 변경
                            case 2 -> menuModifySalesAuthState(DevelopViewLogicConstants.REGISTER_ALL_FILE_MODIFY_STATE, employee, insurance);
                        }
                    }
                    case 2 -> {
                        switch (employee.registerAuthSrActuaryVerification(insurance)) {
                            // 파일 업로드 성공
                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                            // 파일 업로드 취소
                            case -1 -> { continue; }
                            // 파일 업로드 변경
                            case 0 -> {
                                switch (br.verifyCategory(DevelopViewLogicConstants.ERROR_EXIST_FILE_IS_CHANGE_FILE, CommonConstants.CATEGORY_YES_OR_NO)){
                                    case 1 -> {
                                        switch (employee.registerAuthSrActuaryVerification(insurance, null)) {
                                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                                            case -1 -> { continue; }
                                        }
                                    }
                                }
                            }
                            // 판매상태 변경
                            case 2 -> menuModifySalesAuthState(DevelopViewLogicConstants.REGISTER_ALL_FILE_MODIFY_STATE, employee, insurance);
                        }
                    }
                    case 3 -> {
                        switch (employee.registerAuthIsoVerification(insurance)) {
                            // 파일 업로드 성공
                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                            // 파일 업로드 취소
                            case -1 -> { continue; }
                            // 파일 업로드 변경
                            case 0 -> {
                                switch (br.verifyCategory(DevelopViewLogicConstants.ERROR_EXIST_FILE_IS_CHANGE_FILE, CommonConstants.CATEGORY_YES_OR_NO)){
                                    case 1 -> {
                                        switch (employee.registerAuthIsoVerification(insurance, null)) {
                                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                                            case -1 -> { continue; }
                                        }
                                    }
                                }
                            }
                            // 판매상태 변경
                            case 2 -> menuModifySalesAuthState(DevelopViewLogicConstants.REGISTER_ALL_FILE_MODIFY_STATE, employee, insurance);
                        }
                    }
                    case 4 -> {
                        switch (employee.registerAuthFssOfficialDoc(insurance)) {
                            // 파일 업로드 성공
                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                            // 파일 업로드 취소
                            case -1 -> { continue; }
                            // 파일 업로드 변경
                            case 0 -> {
                                switch (br.verifyCategory(DevelopViewLogicConstants.ERROR_EXIST_FILE_IS_CHANGE_FILE, CommonConstants.CATEGORY_YES_OR_NO)){
                                    case 1 -> {
                                        switch (employee.registerAuthFssOfficialDoc(insurance, null)) {
                                            case 1 -> System.out.println(DevelopViewLogicConstants.PRINT_SUCCESS_UPLOAD_SAF);
                                            case -1 -> { continue; }
                                        }
                                    }
                                }
                            }
                            // 판매상태 변경
                            case 2 -> menuModifySalesAuthState(DevelopViewLogicConstants.REGISTER_ALL_FILE_MODIFY_STATE, employee, insurance);
                        }
                    }
                    case 0 -> {
                        break loop;
                    }
                }
            }
            catch (MyFileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void menuModifySalesAuthState(String query, Employee employee, Insurance insurance) {
        switch (br.verifyCategory(query, CommonConstants.CATEGORY_YES_OR_NO)){
            case 1 -> employee.modifySalesAuthState(insurance, SalesAuthorizationState.PERMISSION);
            case 2 -> employee.modifySalesAuthState(insurance, SalesAuthorizationState.DISALLOWANCE);
        }
    }
}
