package com.mju.insuranceCompany.application.viewlogic;

import com.mju.insuranceCompany.application.domain.accident.*;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.customer.payment.BankType;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import com.mju.insuranceCompany.application.global.constant.CompensationViewLogicConstants;
import com.mju.insuranceCompany.application.global.constant.CustomerViewLogicConstants;
import com.mju.insuranceCompany.application.global.constant.ExceptionConstants;
import com.mju.insuranceCompany.application.global.exception.*;
import com.mju.insuranceCompany.application.global.utility.*;
import com.mju.insuranceCompany.application.viewlogic.dto.compDto.AccountRequestDto;
import com.mju.insuranceCompany.application.viewlogic.dto.compDto.AssessDamageResponseDto;
import com.mju.insuranceCompany.application.viewlogic.dto.compDto.InvestigateDamageRequestDto;
import com.mju.insuranceCompany.application.dao.accident.AccidentDao;
import com.mju.insuranceCompany.application.dao.accident.AccidentDaoImpl;
import com.mju.insuranceCompany.application.dao.accident.AccidentDocumentFileDao;
import com.mju.insuranceCompany.application.dao.accident.AccidentDocumentFileDaoImpl;
import com.mju.insuranceCompany.application.dao.customer.CustomerDao;
import com.mju.insuranceCompany.application.dao.customer.CustomerDaoImpl;

import com.mju.insuranceCompany.application.global.utility.FileDialogUtil;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;
import com.mju.insuranceCompany.outerSystem.Bank;

import java.io.InputStreamReader;
import java.util.List;


/**
 * packageName :  main.domain.viewUtils.viewlogic
 * fileName : CompVIewLogic
 * author :  규현
 * date : 2022-05-10
 * description : ViewLogic의 구현클래스, 보상팀의 업무 목록과
 * 그것을 실행시키는 기능이 있다.
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-10                규현             최초 생성
 */
public class CompensationViewLogic implements ViewLogic {

    private AccidentDao accidentDao;
    private AccidentDocumentFileDao accidentDocumentFileDao;
    private CustomerDao customerList;
    private MyBufferedReader br;
    private Employee employee;

    public CompensationViewLogic() {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
    }

    public CompensationViewLogic(Employee employee) {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.employee = employee;
    }

    @Override
    public String showMenu() {
       return MenuUtil.createMenuAndLogout(CompensationViewLogicConstants.COMPENSATION_MENU_HEAD, CompensationViewLogicConstants.MENU_ELEMENTS_COMP_VIEW_LOGIC);
    }

    @Override
    public void work(String command) {

        try {

            switch (command) {
                case CommonConstants.ONE:
                    showAccidentList();
                    break;
                case CommonConstants.TWO:
                    investigateDamage();
                    break;
                case CommonConstants.THREE:
                    assessDamage();
                    break;
                case CommonConstants.ZERO:
                    break;
                default:
                    throw new InputInvalidMenuException(ExceptionConstants.INPUT_INVALID_MENU_EXCEPTION);
            }
        } catch (InputInvalidMenuException e) {
            System.out.println(e.getMessage());
        }
    }

    private void investigateDamage() {
        Accident accident = selectAccident();
        if(accident == null)
            return;
        showAccidentDetail(accident);

        downloadAccDocFile(accident);
        System.out.println(CompensationViewLogicConstants.FINISH_DOWNLOAD);

        InvestigateDamageRequestDto dto = new InvestigateDamageRequestDto();
        dto.setAccidentType(accident.getAccidentType());

        if(accident.getAccidentType() == AccidentType.CARACCIDENT)
            inputErrorRate(dto); // 과실비율 입력
        inputLossReserve(dto); // 지급 준비금 입력.
        if (!accident.getAccDocFileList().containsKey(AccDocType.INVESTIGATEACCIDENT)) {
            System.out.println(CompensationViewLogicConstants.INVESTIGATE_ACCIDENT_QUERY);
        }
        employee.investigateDamage(dto,accident);


        while (true) {
            String rtVal = "";
            rtVal = (String) br.verifyRead(CompensationViewLogicConstants.ASSESS_DAMAGE_QUERY,rtVal);
            if (rtVal.equals(CommonConstants.YES)) {
                assessDamage();
                break;
            } else if (rtVal.equals(CommonConstants.NO)) {
                break;
            }
        }

    }

    private void showAccidentList() {
        List<Accident> accidents = getAccidentList();
        if (accidents == null) return;
        showAccidentInfo(accidents);
    }

    private Accident selectAccident() {
        List<Accident> accidents = getAccidentList();
        if (accidents == null) return null;
        while (true) {
            try {
                System.out.println(CompensationViewLogicConstants.SELECT_ACCIDENT);
                showAccidentInfo(accidents);
                System.out.println(CommonConstants.LIST_LINE);
                int accidentId = 0;
                accidentId = (int) br.verifyRead(CompensationViewLogicConstants.ACCIDENT_ID_QUERY, accidentId);
                if (accidentId == 0) {
                    return null;
                }
                accidentDao = new AccidentDaoImpl();
                Accident accident = this.accidentDao.read(accidentId);
                if (accident.getEmployeeId() != this.employee.getId()) {
                    throw new MyIllegalArgumentException(ExceptionConstants.INPUT_DATA_ON_LIST);
                }
                accidentDocumentFileDao = new AccidentDocumentFileDaoImpl();
                List<AccidentDocumentFile> accidentDocumentFiles = accidentDocumentFileDao.readAllByAccidentId(accidentId);
                for (AccidentDocumentFile accidentDocumentFile : accidentDocumentFiles) {
                    accident.getAccDocFileList().put(accidentDocumentFile.getType(), accidentDocumentFile);
                }
                return accident;
            } catch (MyIllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Accident> getAccidentList() {
        List<Accident> accidents = null;
        try {
            accidents = employee.readAccident();
        } catch (NoResultantException e) {
            System.out.println(e.getMessage());
        }
        return accidents;
    }

    private void showAccidentInfo(List<Accident> accidents) {
        for (Accident accident : accidents) {
            accident.printForComEmployee();
        }
    }

    private void showAccidentDetail(Accident accident) {
        customerList = new CustomerDaoImpl();
        Customer customer = customerList.read(accident.getCustomerId());


        accident.printForComEmployee();
        System.out.println(CompensationViewLogicConstants.SHOW_CUSTOMER_NAME + customer.getName());
        AccidentType accidentType = accident.getAccidentType();
        switch (accidentType) {
            case CARACCIDENT -> {
                System.out.println(CompensationViewLogicConstants.SHOW_CAR_NO + ((CarAccident)accident).getCarNo());
                System.out.println(CompensationViewLogicConstants.SHOW_OPPOSING_PHONE + ((CarAccident)accident).getOpposingDriverPhone());
                System.out.println(CompensationViewLogicConstants.SHOW_PLACE_ADDRESS + ((CarAccident)accident).getPlaceAddress());
            }
            case INJURYACCIDENT ->
                System.out.println(CompensationViewLogicConstants.SHOW_INJURY_SITE + ((InjuryAccident)accident).getInjurySite());

            case FIREACCIDENT ->
                System.out.println(CompensationViewLogicConstants.SHOW_PLACE_ADDRESS + ((FireAccident)accident).getPlaceAddress());

        }

    }

    private void assessDamage() {
        Accident accident = null;
        AccountRequestDto compAccount = null;
        while (true) {
            try {
                accident = selectAccident();
                if (accident == null) // 배정된 사고가 없을 떄 null. 이거도 exception인가???
                    return;
                isValidAccident(accident);
                downloadAccDocFile(accident);
                compAccount = createCompAccount();
                break;

            } catch (MyInvalidAccessException e) {
                System.out.println(e.getMessage());
            }

        }
        //다운로드 하기.


        System.out.println(CompensationViewLogicConstants.UPLOAD_ASSESS_DAMAGE);
        AssessDamageResponseDto assessDamageResponseDto = null;
        try {
            assessDamageResponseDto  = this.employee.assessDamage(accident, compAccount);
        } catch (MyInvalidAccessException e) {
            System.out.println(e.getMessage());
            return;
        }

        long lossReserves = accident.getLossReserves();
        long compensation = 0L;
        compensation = (long) br.verifyRead(CompensationViewLogicConstants.getInputCompensation(lossReserves), compensation);
        if (compensation > lossReserves * 1.5) {
            System.out.println(CompensationViewLogicConstants.REJECT_ASSESS_DAMAGE);
            return;
        }

        if (accident.getAccidentType() == AccidentType.CARACCIDENT) {
            int errorRate = 0;
            errorRate = ((CarAccident)accident).getErrorRate();
            compensation = (long) (compensation * (((double)errorRate/100)));
            if (compensation == 0) {
                System.out.println(CompensationViewLogicConstants.NO_ERROR);
                return;
            }
        }
        Bank.sendCompensation(assessDamageResponseDto.getAccount(),compensation);
        try {
            accidentDao = new AccidentDaoImpl();
            accidentDao.delete(accident.getId());
            FileDialogUtil.deleteDir(accident); // 폴더 삭제
        } catch (MyIllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void isValidAccident(Accident accident) {
        if (accident.getLossReserves() == 0 ) {
            throw new MyInvalidAccessException(CompensationViewLogicConstants.LOSS_RESERVE_EXCEPTION);
        }
        if (!accident.getAccDocFileList().containsKey(AccDocType.INVESTIGATEACCIDENT)) {
            throw new MyInvalidAccessException(CompensationViewLogicConstants.INVESTIGATE_ACCIDENT_EXCEPTION);
        }
        if (accident instanceof CarAccident car && car.getErrorRate() == 0) {
                throw new MyInvalidAccessException(CompensationViewLogicConstants.ERROR_RATE_EXCEPTION);
        }
    }

    private AccountRequestDto createCompAccount() {
        AccountRequestDto account = null;
        loop : while (true) {

            System.out.println(CustomerViewLogicConstants.REGISTER_ACCOUNT);
            System.out.println(CustomerViewLogicConstants.SELECT_BANK);
            BankType bankType = BankUtil.selectBankType(br);
            if(bankType==null)
               throw new MyInvalidAccessException(CompensationViewLogicConstants.INPUT_ACCOUNT);
            while (true) {
                try {
                    StringBuilder query = new StringBuilder();
                    query.append(CustomerViewLogicConstants.showAccountNoEX(bankType.getFormat())).append(CommonConstants.LINE_SEPARATOR).append(CommonConstants.INPUT);
                    String command = "";
                    command = (String) br.verifyRead(query.toString(),command);
                    if (command.equals(CommonConstants.ZERO)) {
                        continue loop;
                    }
                    String accountNo = BankUtil.checkAccountFormat(bankType,command);
                    account = AccountRequestDto.builder().bankType(bankType)
                            .accountNo(accountNo)
                            .build();
                    break loop;
                } catch (MyInadequateFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return account;
    }



    private void inputLossReserve(InvestigateDamageRequestDto accident) {
        while (true) {
            try {
                long loss_reserve = -1;
                loss_reserve = (long) br.verifyRead(CompensationViewLogicConstants.LOSS_RESERVE_QUERY, loss_reserve);
                if (loss_reserve < 0) {
                    throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
                }
                accident.setLossReserves(loss_reserve);
                break;
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputErrorRate(InvestigateDamageRequestDto accident) {

            while (true) {
                try{
                    int errorRate = -1;
                    errorRate = (int) br.verifyRead(CompensationViewLogicConstants.ERROR_RATE_QUERY,errorRate);
                    if (FormatUtil.isErrorRate(errorRate)) {
                        accident.setErrorRate(errorRate);
                        break;
                    } else {
                        throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
                    }
                }catch (MyInadequateFormatException e) {
                    System.out.println(e.getMessage());
                }
            }

    }

    private void downloadAccDocFile(Accident accident) {
        for (AccidentDocumentFile accidentDocumentFile : accident.getAccDocFileList().values()) {
            label:
            while (true) {
                String query = CompensationViewLogicConstants.getDownloadDocExQuery(accidentDocumentFile.getType().getDesc());
                String result = "";
                result = (String) br.verifyRead(query,result);
                switch (result) {
                    case CommonConstants.YES:
                        FileDialogUtil.download(accidentDocumentFile.getFileAddress());
                        break label;
                    case CommonConstants.NO:
                        break label;
                    case CommonConstants.ZERO:
                        return;
                }
            }
        }
    }
}
