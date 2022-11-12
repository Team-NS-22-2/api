package com.mju.insuranceCompany.application.viewlogic;

import com.mju.insuranceCompany.application.domain.contract.ConditionOfUw;
import com.mju.insuranceCompany.application.domain.contract.Contract;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import com.mju.insuranceCompany.application.global.constant.UnderwritingViewLogicConstants;
import com.mju.insuranceCompany.application.global.exception.*;
import com.mju.insuranceCompany.application.global.utility.MenuUtil;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;
import com.mju.insuranceCompany.application.dao.contract.ContractDaoImpl;
import com.mju.insuranceCompany.application.dao.customer.CustomerDaoImpl;
import com.mju.insuranceCompany.application.dao.insurance.InsuranceDaoImpl;


import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * packageName :  main.domain.viewUtils.viewlogic
 * fileName : UWViewLogic
 * author :  규현
 * date : 2022-05-10
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-10                규현             최초 생성
 */
public class UnderwritingViewLogic implements ViewLogic {

    private Scanner sc;
    private MyBufferedReader br;
    private Employee employee;

    public UnderwritingViewLogic(Employee employee) {
        this.sc = new Scanner(System.in);
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.employee = employee;
    }

    @Override
    public String showMenu() {
        return MenuUtil.createMenuAndLogout(UnderwritingViewLogicConstants.UNDERWRITING_MENU, UnderwritingViewLogicConstants.UNDERWRITING_MENU_ELEMENTS);
    }

    @Override
    public void work(String command) {
        boolean isExit = false;

        while (isExit != true) {

            try {
                switch (command) {
                    case CommonConstants.ONE -> isExit = selectInsuranceType();
                    case CommonConstants.ZERO -> isExit = true;
                    default -> throw new InputInvalidMenuException();
                }

            } catch (InputInvalidMenuException e) {
                System.out.println(e.getMessage());
                command = sc.next();
            }
        }

    }

    public boolean selectInsuranceType() {
        boolean isExit = false;

        while (isExit != true) {

            try {
                String command = String .valueOf(br.verifyMenu(
                        MenuUtil.createMenuAndExitQuery(UnderwritingViewLogicConstants.INSURANCE_TYPE_MENU, UnderwritingViewLogicConstants.INSURANCE_TYPE_MENU_ELEMENTS),
                        UnderwritingViewLogicConstants.INSURANCE_TYPE_MENU_ELEMENTS.length
                ));

                InsuranceType insuranceType = null;

                switch (command) {
                    case CommonConstants.ONE -> { insuranceType = InsuranceType.HEALTH; readContracts(insuranceType); }
                    case CommonConstants.TWO -> { insuranceType = InsuranceType.CAR; readContracts(insuranceType); }
                    case CommonConstants.THREE -> { insuranceType = InsuranceType.FIRE; readContracts(insuranceType); }
                    case CommonConstants.ZERO -> isExit = true;
                    case CommonConstants.EXIT -> throw new MyCloseSequence();
                    default -> throw new InputInvalidMenuException();
                }
            } catch (InputInvalidMenuException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    public void readContracts(InsuranceType insuranceType) {
        boolean isExit = false;

        while (isExit != true) {

            try {
                // 계약 목록 조회 (read)
                ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
                List<Contract> contractList = contractDaoImpl.readAllByInsuranceType(insuranceType);

                if (contractList.size() == 0) {
                    isExit = true;
                    throw new MyNotExistContractException();
                }
                MenuUtil.createMenu(CommonConstants.LIST_LINE);
                MenuUtil.createMenu(UnderwritingViewLogicConstants.READ_CONTRACTS_COLUMN);
                printContractList(contractList);
                MenuUtil.createMenu(CommonConstants.LIST_LINE);

                // 계약 ID 입력
                MenuUtil.createMenuAndExit(UnderwritingViewLogicConstants.INPUT_CONTRACT_ID);
                String contractId = "";
                contractId = (String) br.verifyRead(CommonConstants.INPUT, contractId);

                if (contractId.equals(CommonConstants.ZERO)) break;
                if (contractId.equals(CommonConstants.EXIT)) throw new MyCloseSequence();

                // 계약 정보 조회(read)
                Contract contract = this.employee.readContract(Integer.parseInt(contractId), insuranceType);
                MenuUtil.createMenu(UnderwritingViewLogicConstants.SHOW_CONTRACT_INFO);
                printContractInfo(contract);

                selectUwState(contract);

            } catch (MyNotExistContractException | MyIllegalArgumentException | InputInvalidDataException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(new InputInvalidDataException().getMessage());
            }
        }
    }


    public boolean selectUwState(Contract contract) {
        boolean isExit = false;

        while (isExit != true) {

            try {
                // 인수심사 결과 선택
                String command = String.valueOf(br.verifyMenu(
                        MenuUtil.createMenuOnlyExitQuery(UnderwritingViewLogicConstants.SELECT_UNDERWRITING_STATE, UnderwritingViewLogicConstants.SELECT_UNDERWRITING_STATE_MENU),
                        UnderwritingViewLogicConstants.SELECT_UNDERWRITING_STATE_MENU.length
                ));

                switch (command) {

                    case CommonConstants.ONE: case CommonConstants.TWO: case CommonConstants.THREE:
                        // 인수 사유 입력
                        MenuUtil.createMenu(UnderwritingViewLogicConstants.INPUT_UNDERWRITING_REASON_MENU);
                        String reasonOfUw = "";
                        reasonOfUw = (String) br.verifyRead(UnderwritingViewLogicConstants.INPUT_UNDERWRITING_REASON, reasonOfUw);
                        ConditionOfUw conditionOfUw = null;

                        switch (command) {
                            case CommonConstants.ONE -> conditionOfUw = ConditionOfUw.APPROVAL;
                            case CommonConstants.TWO -> conditionOfUw = ConditionOfUw.REFUSE;
                            case CommonConstants.THREE -> conditionOfUw = ConditionOfUw.RE_AUDIT;
                            default -> new InputInvalidMenuException();
                        }
                        isExit = confirmUnderWriting(contract.getId(), reasonOfUw, conditionOfUw);
                        break;

                    case CommonConstants.FOUR:
                        return false;
                    case CommonConstants.EXIT:
                        throw new MyCloseSequence();
                    default:
                        throw new InputInvalidMenuException();
                }
            } catch (InputInvalidMenuException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;

    }


    public boolean confirmUnderWriting(int contractId, String reasonOfUw, ConditionOfUw conditionOfUw) {
        boolean isExit = false;

        while (isExit != true) {

            try {
                // 인수 심사 결과 반영 확인
                String command = String.valueOf(br.verifyMenu(
                        MenuUtil.createMenuOnlyExitQuery(UnderwritingViewLogicConstants.CONFIRM_UNDERWRITING_MENU, UnderwritingViewLogicConstants.CONFIRM_UNDERWRITING_MENU_ELEMENTS),
                        UnderwritingViewLogicConstants.CONFIRM_UNDERWRITING_MENU_ELEMENTS.length
                ));

                switch (command) {
                    case CommonConstants.ONE:
                        // update
                        this.employee.underwriting(contractId, reasonOfUw, conditionOfUw);

                        MenuUtil.createMenu(UnderwritingViewLogicConstants.CONFIRM_UNDERWRITING_MESSAGE);
                        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
                        System.out.println(contractDaoImpl.read(contractId));
                        isExit = true;
                        break;
                    case CommonConstants.TWO:
                        return false;
                    case CommonConstants.EXIT:
                        throw new MyCloseSequence();
                    default:
                        throw new InputInvalidMenuException();
                }
            } catch (InputInvalidMenuException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;

    }

    public void printContractList(List<Contract> contractList) {

        for (Contract contract : contractList) {
            CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
            Customer customer = customerDaoImpl.read(contract.getCustomerId());
            System.out.println(contract.getId() + "      | " + customer.getName() + "    | " + contract.getConditionOfUw().getName());
        }
    }

    public Contract printContractInfo(Contract contract) {
        System.out.println(contract.toString());

        CustomerDaoImpl customerDaoImpl = new CustomerDaoImpl();
        Customer customer = customerDaoImpl.read(contract.getCustomerId());
        System.out.println(customer.toString());

        InsuranceDaoImpl insuranceDaoImpl = new InsuranceDaoImpl();
        Insurance insurance = insuranceDaoImpl.read(contract.getInsuranceId());
        System.out.println(insurance.printOnlyInsurance());

        return contract;

    }
}
