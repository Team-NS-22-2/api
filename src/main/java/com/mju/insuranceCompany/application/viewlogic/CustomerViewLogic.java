package com.mju.insuranceCompany.application.viewlogic;

import com.mju.insuranceCompany.application.dao.accident.AccidentDaoImpl;
import com.mju.insuranceCompany.application.dao.accident.AccidentDocumentFileDao;
import com.mju.insuranceCompany.application.dao.accident.AccidentDocumentFileDaoImpl;
import com.mju.insuranceCompany.application.dao.contract.ContractDao;
import com.mju.insuranceCompany.application.dao.contract.ContractDaoImpl;
import com.mju.insuranceCompany.application.dao.insurance.InsuranceDao;
import com.mju.insuranceCompany.application.dao.insurance.InsuranceDaoImpl;
import com.mju.insuranceCompany.application.domain.accident.Accident;
import com.mju.insuranceCompany.application.domain.accident.AccidentType;
import com.mju.insuranceCompany.application.domain.accident.CarAccident;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.application.domain.contract.BuildingType;
import com.mju.insuranceCompany.application.domain.contract.CarType;
import com.mju.insuranceCompany.application.domain.contract.Contract;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.customer.payment.*;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.domain.insurance.Guarantee;
import com.mju.insuranceCompany.application.domain.insurance.Insurance;
import com.mju.insuranceCompany.application.domain.insurance.InsuranceType;
import com.mju.insuranceCompany.application.domain.insurance.SalesAuthorizationState;
import com.mju.insuranceCompany.application.global.constant.CommonConstants;
import com.mju.insuranceCompany.application.global.constant.ContractConstants;
import com.mju.insuranceCompany.application.global.constant.CustomerViewLogicConstants;
import com.mju.insuranceCompany.application.global.constant.ExceptionConstants;
import com.mju.insuranceCompany.application.global.exception.*;
import com.mju.insuranceCompany.application.global.utility.*;
import com.mju.insuranceCompany.application.viewlogic.dto.UserDto.UserDto;
import com.mju.insuranceCompany.application.viewlogic.dto.accidentDto.AccidentReportDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.*;
import com.mju.insuranceCompany.outerSystem.CarAccidentService;
import com.mju.insuranceCompany.application.dao.accident.AccidentDao;

import com.mju.insuranceCompany.application.global.utility.FileDialogUtil;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;

import com.mju.insuranceCompany.application.viewlogic.dto.customerDto.CustomerDto;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * packageName :  main.domain.viewUtils.viewlogic
 * fileName : CustomerViewLogic
 * author :  규현
 * date : 2022-05-10
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-10                규현             최초 생성
 */
public class CustomerViewLogic implements ViewLogic {

    private ContractDao contractList;
    private InsuranceDao insuranceList;
    private AccidentDao accidentDao;
    private AccidentDocumentFileDao accidentDocumentFileDao;

    private Customer customer;
    private Scanner sc;
    private MyBufferedReader br;
    private Insurance insurance;

    public CustomerViewLogic() {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.sc = new Scanner(System.in);
        this.customer = new Customer();
    }

    public CustomerViewLogic(Customer customer) {
        this.br = new MyBufferedReader(new InputStreamReader(System.in));
        this.sc = new Scanner(System.in);
        this.customer = customer;
        setPayment();
    }
    // customer ID를 입력하여 customerViewLogic에서 진행되는 작업에서 사용되는 고객 정보를 불러온다.
    private void setPayment() {
        try {
            customer.readPayments();
        } catch (MyIllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public String showMenu() {
        if (customer.getId() == 0)
            return MenuUtil.createMenuAndExitQuery(CustomerViewLogicConstants.CUSTOMER_MENU, ContractConstants.MENU_ELEMENT_GUEST_VIEW_LOGIC);
        else
            return MenuUtil.createMenuAndLogout(CustomerViewLogicConstants.CUSTOMER_MENU, CustomerViewLogicConstants.SIGN_IN_INSURANCE, CustomerViewLogicConstants.PAY_PREMIUM, CustomerViewLogicConstants.REPORT_ACCIDENT, CustomerViewLogicConstants.CLAIM_COMPENSATION);
    }

    @Override
    public void work(String command) {
        try {
            if (customer.getId() == 0) {
                switch (command) {
                    case CommonConstants.ONE -> selectInsurance();
                }
            } else {
                switch (command) {
                    case CommonConstants.ONE -> selectInsurance();
                    case CommonConstants.TWO -> payPremiumButton();
                    case CommonConstants.THREE -> reportAccident();
                    case CommonConstants.FOUR -> claimCompensation();
                }
            }
        } catch (MyIllegalArgumentException | NoResultantException | InputNullDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectInsurance() {
        ArrayList<Insurance> insurances = customer.readInsurances();
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
                System.out.println(ContractConstants.CUSTOMER_SELECT_INSURANCE_ID );
                insuranceId = (int) br.verifyRead(ContractConstants.CONTRACT_INPUT_INSURANCE_ID, insuranceId);
                if (insuranceId == 0) break;

                insurance = customer.readInsurance(insuranceId);
                if (insurance.getDevelopInfo().getSalesAuthorizationState() == SalesAuthorizationState.PERMISSION) {
                    System.out.println(ContractConstants.CONTRACT_INSURANCE_DESCRIPTION + insurance.getDescription() + ContractConstants.CONTRACT_INSURANCE_CONTRACT_PERIOD + insurance.getContractPeriod() +
                            ContractConstants.CONTRACT_YEAR_PARAMETER + ContractConstants.CONTRACT_INSURANCE_PAYMENT_PERIOD + insurance.getPaymentPeriod() + ContractConstants.CONTRACT_YEAR_PARAMETER + ContractConstants.CONTRACT_INSURANCE_GUARANTEE);
                    System.out.printf(ContractConstants.CONTRACT_GUARANTEES_CATEGORY_FORMAT, "", ContractConstants.CONTRACT_INSURANCE_GUARANTEE_DESCRIPTION, ContractConstants.CONTRACT_INSURANCE_GUARANTEE_AMOUNT);
                    System.out.println(ContractConstants.CONTRACT_LONG_DIVISION);
                    for(Guarantee guarantee : insurance.getGuaranteeList()){
                        System.out.printf(ContractConstants.CONTRACT_GUARANTEES_VALUE_FORMAT, guarantee.getName(), guarantee.getDescription(), guarantee.getGuaranteeAmount());
                    }
                    decideSigning();
                }
                else {
                    throw new MyIllegalArgumentException(ContractConstants.exceptionNoInsurance(insurance.getId()));
                }
            } catch (InputException | MyIllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void decideSigning() {
        int choice = br.verifyCategory(ContractConstants.CUSTOMER_DICIDE_SIGNING, CommonConstants.CATEGORY_YES_OR_NO);
        CustomerDto customerDto = null;
        if (choice == 1) {
            if (customer.getId() == 0)
                customerDto = inputCustomerInfo();
            else
                customerDto = new CustomerDto(customer);
            ContractDto contractDto = switch (insurance.getInsuranceType()) {
                case HEALTH -> inputHealthInfo(customerDto);
                case FIRE -> inputFireInfo();
                case CAR -> inputCarInfo(customerDto);
            };
            signContract(customerDto, contractDto);
        }
    }

    private CustomerDto inputCustomerInfo() {
        String name = "", ssn = "", phone = "", address = "", email = "", job = "";
        System.out.println(ContractConstants.CONTRACT_INPUT_CUSTOMER_INFO);

        name = (String) br.verifySpecificRead(ContractConstants.CONTRACT_CUSTOMER_NAME_QUERY, name, "name");
        ssn = (String) br.verifySpecificRead(ContractConstants.CONTRACT_SSN_QUERY, ssn, "ssn");
        phone = (String) br.verifySpecificRead(ContractConstants.CONTRACT_PHONE_QUERY, phone, "phone");
        address = (String) br.verifyRead(ContractConstants.CONTRACT_ADDRESS_QUERY, address);
        email = (String) br.verifySpecificRead(ContractConstants.CONTRACT_EMAIL_QUERY, email, "email");
        job = (String) br.verifyRead(ContractConstants.CONTRACT_JOB_QUERY, job);

        return new CustomerDto(name, ssn, phone, address, email, job);
    }

    private ContractDto inputHealthInfo(CustomerDto customerDto) {
        int riskCount = 0, height = 0, weight = 0;
        String diseaseDetail = "";
        boolean isDrinking, isSmoking, isDriving, isDangerActivity, isTakingDrug, isHavingDisease;
        System.out.println(ContractConstants.CONTRACT_INPUT_HEALTH_INFO);

        height = (int) br.verifyRead(ContractConstants.CONTRACT_HEIGHT_QUERY, height);
        weight = (int) br.verifyRead(ContractConstants.CONTRACT_WEGHIT_QUERY, weight);
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
        if (isHavingDisease) {
            riskCount++;
            diseaseDetail = (String) br.verifyRead(ContractConstants.CONTRACT_DISEASE_DETAIL_QUERY, diseaseDetail);
        }

        String ssn;
        if (customerDto == null)
            ssn = customer.getSsn();
        else
            ssn = customerDto.getSsn();

        int premium = customer.inquireHealthPremium(ssn, riskCount, insurance);
        return new HealthContractDto(height, weight, isDrinking, isSmoking, isDriving, isDangerActivity,
                                    isTakingDrug, isHavingDisease, diseaseDetail).setPremium(premium);
    }

    private ContractDto inputFireInfo() {
        int buildingArea = 0;
        BuildingType buildingType;
        Long collateralAmount = 0L;
        boolean isSelfOwned, isActualResidence;

        buildingArea = (int) br.verifyRead(ContractConstants.CONTRACT_BUILDING_AREA_QUERY, buildingArea);
        buildingType = switch (br.verifyCategory(ContractConstants.CONTRACT_BUILDING_TYPE_QUERY, CommonConstants.CATEGORY_FOUR)) {
            case 1 -> BuildingType.COMMERCIAL;
            case 2 -> BuildingType.INDUSTRIAL;
            case 3 -> BuildingType.INSTITUTIONAL;
            case 4 -> BuildingType.RESIDENTIAL;
            default -> throw new IllegalStateException();
        };
        collateralAmount = (Long) br.verifyRead(ContractConstants.CONTRACT_COLLATERAL_AMOUNT_QUERY, collateralAmount);
        isSelfOwned = br.verifyCategory(ContractConstants.CONTRACT_IS_SELF_OWNED_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;
        isActualResidence = br.verifyCategory(ContractConstants.CONTRACT_IS_ACTUAL_RESIDENCE_QUERY, CommonConstants.CATEGORY_YES_OR_NO) == 1;

        int premium = customer.inquireFirePremium(buildingType, collateralAmount, insurance);
        return new FireContractDto(buildingArea, buildingType, collateralAmount, isSelfOwned, isActualResidence).setPremium(premium);
    }

    private ContractDto inputCarInfo(CustomerDto customerDto) {
        CarType carType;
        String modelName = "", carNo = "";
        int modelYear = 0;
        Long value = 0L;

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
        value = (Long) br.verifyRead(ContractConstants.CONTRACT_VALUE_QUERY, value);

        String ssn;
        if (customerDto == null)
            ssn = customer.getSsn();
        else
            ssn = customerDto.getSsn();

        int premium = customer.inquireCarPremium(ssn, value, insurance);
        return new CarContractDto(carNo, carType, modelName, modelYear, value).setPremium(premium);
    }

    private void signContract(CustomerDto customerDto, ContractDto contractDto) {
        System.out.println(ContractConstants.premiumInquiry(contractDto.getPremium()));

        int choice = br.verifyCategory(ContractConstants.CUSTOMER_SGIN_CONTRACT, CommonConstants.CATEGORY_YES_OR_NO);
        switch (choice) {
            case 1 -> {
                if (customer.getId() == 0) {
                    customer = customer.registerCustomer(customerDto);
                    UserDto userDto = signUp();
                    customer.registerUser(userDto);
                }
                Contract contract = customer.registerContract(customer, contractDto, insurance);
                System.out.println(customer);
                System.out.println(contract);
                System.out.println(ContractConstants.CUSTOMER_SIGN);
            }
            case 2 -> System.out.println(ContractConstants.CUSTOMER_CANCEL);
        }
    }

    private UserDto signUp() {
        String userId = "", password = "";

        System.out.println(ContractConstants.CONTRACT_SIGN_UP);
        userId = (String) br.verifyRead(ContractConstants.CONTRACT_USER_ID_QUERY, userId);
        password = (String) br.verifyRead(ContractConstants.CONTRACT_USER_PASSWORD_QUERY, password);
        return new UserDto(userId, password, customer.getId());
    }




    // 보험료 납입 버튼을 클릭했을 경우, 그 이후 작업들에 대해서 보여준다
    // 이후 진행될 작업으로 보험료를 납입할 계약을 선택하고, 해당 계약으로 즉시 결제를 할지, 계약에 기존에 등록된 결제수단을 등록할지,
    // 고객에게 새로운 결제 수단을 추가할지 정할 수 있다.
    private void payPremiumButton() {
        while (true) {
            Contract contract = selectContract();
            if (contract == null) {
                System.out.println(CommonConstants.CANCEL);
                return;
            }
            loop : while (true) {
                try {
                    MenuUtil.createMenu(CustomerViewLogicConstants.PAY_MENU, CustomerViewLogicConstants.DO_PAY, CustomerViewLogicConstants.SET_PAYMENT, CustomerViewLogicConstants.ADD_ACCOUNT_MENU_HEAD);
                    System.out.println(CommonConstants.ZERO_MESSAGE);
                    System.out.println(CommonConstants.EXIT_MESSAGE);
                    System.out.print(CommonConstants.INPUT);
                    String next = sc.next();
                    switch (next.toUpperCase()) {
                        case CommonConstants.ONE:
                            payLogic(contract);
                            break;
                        case CommonConstants.TWO:
                            setPaymentOnContract(contract);
                            break;
                        case CommonConstants.THREE:
                            addNewPayment();
                            break;
                        case CommonConstants.ZERO:
                            break loop;
                        case CommonConstants.EXIT:
                            throw new MyCloseSequence();
                        default:
                            throw new InputInvalidMenuException();
                    }
                } catch (InputInvalidMenuException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // 고객이 보험료 납입 버튼을 클릭한 이후 사용할 계약을 선택하는 기능이다.
    // 계약의 ID를 입력하는 것으로 이후 작업이 진행될 계약 객체를 선택한다.
    private Contract selectContract(){
        Contract contract = null;
        List<Contract> contracts = customer.readContracts();
        while (true) {
            try {
                try {
                    System.out.println(CustomerViewLogicConstants.CONTRACT_LIST);
                    for (Contract con : contracts) {
                        showContractInfoForPay(con);
                    }
                    System.out.println(CommonConstants.ZERO_MESSAGE);
                    System.out.print(CommonConstants.INPUT);
                    String key = sc.next();
                    if (key.equals(CommonConstants.ZERO))
                        break;
                    contractList = new ContractDaoImpl();
                    contract = contractList.read(Integer.parseInt(key));
                    if (contract.getCustomerId() != this.customer.getId()) {
                        throw new MyIllegalArgumentException(ExceptionConstants.INPUT_DATA_ON_LIST);
                    }

                    break;
                } catch (MyIllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    throw new InputInvalidDataException(e);
                }
            } catch (InputInvalidDataException e) {
                System.out.println(e.getMessage());
            }
        }
        return contract;
    }

    // 보험료 납부를 위한 계약 정보를 출력하는 기능
    public void showContractInfoForPay(Contract contract) {

        insuranceList = new InsuranceDaoImpl();
        Insurance insurance = insuranceList.read(contract.getInsuranceId());
        StringBuilder sb = new StringBuilder();
        sb.append(CustomerViewLogicConstants.CONTRACT_ID_LABEL).append(contract.getId())
                .append(CustomerViewLogicConstants.CONTRACT_NAME_LABEL).append(insurance.getName()).append(CustomerViewLogicConstants.CONTRACT_PREMIUM_LABEL).append(contract.getPremium())
                .append("\n");
        System.out.println(sb.toString());
    }


    // 계약을 선택한 이후 즉시 결제를 시도하는 기능.
    // 해당 계약에 결제 수단이 등록되지 않았다면 결제 수단 등록을 진행한다.
    private void payLogic(Contract contract) {
        if (contract.getPaymentId() == 0) {
            System.out.println(CustomerViewLogicConstants.NO_PAYMENT_ON_CONTRACT);
            setPaymentOnContract(contract);
        }else{
            pay(contract);
        }
    }

    // 계약에 대해서 보험료를 납부하는 기능
    private void pay(Contract contract) {
        customer.pay(contract);
    }


    // 고객에게 등록된 결제 수단들을 불러온다.
    private void setPaymentOnContract(Contract contract) {
        ArrayList<Payment> paymentList = this.customer.getPaymentList();
        if (paymentList.isEmpty()) {
            addNewPayment();
            return;
        }
        while (true) {
            try{
                try {
                    System.out.println(CustomerViewLogicConstants.PAYMENT_LIST_MENU);
                    for (Payment payment : paymentList) {
                        System.out.println(payment);
                    }
                    System.out.println(CommonConstants.ZERO_MESSAGE);
                    System.out.println(CommonConstants.EXIT_MESSAGE);
                    System.out.print(CommonConstants.INPUT);
                    String key = sc.next();
                    key = key.toUpperCase();
                    if (key.equals(CommonConstants.ZERO))
                        return;
                    if(key.equals(CommonConstants.EXIT))
                        throw new MyCloseSequence();
                    int paymentId = Integer.parseInt(key);
                    this.customer.registerPayment(contract, paymentId);
                    break;
                } catch (NumberFormatException e) {
                    throw new InputInvalidDataException(ExceptionConstants.INPUT_WRONG_FORMAT, e);
                }
            } catch (MyIllegalArgumentException |InputInvalidDataException e ) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 고객에게 새로운 결제수단을 추가하는 기능. 카드와 계좌의 정보를 추가할 수 있다.
    public void addNewPayment() {
        loop :while (true) {
            try {

                MenuUtil.createMenu(CustomerViewLogicConstants.ADD_ACCOUNT_MENU_HEAD, CustomerViewLogicConstants.REGISTER_CARD, CustomerViewLogicConstants.REGISTER_ACCOUNT);
                System.out.println(CommonConstants.ZERO_MESSAGE);
                System.out.println(CommonConstants.EXIT_MESSAGE);
                System.out.print(CommonConstants.INPUT);
                switch (sc.next().toUpperCase()) {
                    case CommonConstants.ONE:
                        createCard();
                        break;
                    case CommonConstants.TWO:
                        createAccount();
                        break;
                    case CommonConstants.ZERO:
                        break loop;
                    case CommonConstants.EXIT:
                        throw new MyCloseSequence();
                    default:
                        throw new InputInvalidMenuException();
                }
            } catch (InputInvalidMenuException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    // 결제수단 중 카드를 새로 추가하는 기능
    private void createCard() {
        PaymentDto card = new PaymentDto();
        while (true) {
            try {
                System.out.println(CustomerViewLogicConstants.REGISTER_CARD);
                System.out.println(CustomerViewLogicConstants.SELECT_CARD_TYPE);
                CardType cardType = selectCardType();
                if(cardType==null)
                    return;

                while (true) {
                    try {
                        System.out.println(CustomerViewLogicConstants.CARD_NO_EX);
                        System.out.print(CommonConstants.INPUT);
                        String cardNo = validateCardNoFormat(sc.next());
                        System.out.println(CustomerViewLogicConstants.CVC_EX);
                        System.out.print(CommonConstants.INPUT);
                        String cvc = validateCVCFormat(sc.next());
                        System.out.println(CustomerViewLogicConstants.EXPIRY_DATE);
                        System.out.print(CustomerViewLogicConstants.MONTH);
                        int month = validateMonthFormat(sc.nextInt());
                        System.out.println(CustomerViewLogicConstants.YEAR_EX);
                        System.out.print(CommonConstants.INPUT);
                        int year = validateYearFormat(sc.nextInt());
                        LocalDate expireDate = createExpireDate(month, year);

                        card.setCardNo(cardNo)
                                .setCvcNo(cvc)
                                .setCardType(cardType)
                                .setExpiryDate(expireDate)
                                .setCustomerId(this.customer.getId())
                                .setPayType(PayType.CARD);
                        break;
                    } catch ( MyInadequateFormatException e) {
                        System.out.println(ExceptionConstants.INPUT_WRONG_FORMAT);
                    }
                }


                while (true) {
                    System.out.println(CustomerViewLogicConstants.REGISTER_CARD_INFO);
                    System.out.print(CommonConstants.INPUT);
                    String result = sc.next();
                    result = result.toUpperCase();
                    if (result.equals(CommonConstants.NO)) {
                        System.out.println(CustomerViewLogicConstants.CANCEL_REGISTER_PAYMENT);
                        return;
                    } else if (result.equals(CommonConstants.YES))
                        break;
                    else
                        throw new InputInvalidDataException();
                }
                break;

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException | MyInadequateFormatException | InputInvalidDataException e) {
                System.out.println(ExceptionConstants.INPUT_WRONG_FORMAT);
            }
        }
        customer.addPayment(card);
        System.out.println(CustomerViewLogicConstants.SUCCESS_REGISTER_PAYMENT);

    }

    // 카드 결제 수단 추가 중 카드사를 선택하는 기능
    private CardType selectCardType() {
        CardType[] values = CardType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i+1) + CommonConstants.SPACE + values[i]);
        }
        System.out.println(CommonConstants.ZERO_MESSAGE);
        System.out.println(CustomerViewLogicConstants.SELECT_CARD_TYPE_NO);
        System.out.print(CommonConstants.INPUT);
        String key = sc.next();
        if(key.equals(CommonConstants.ZERO))
            return null;
        return values[Integer.parseInt(key)-1];
    }
    // 카드 결제 수단을 추가하는 과정에서 만료기간 중 연도를 형식에 맞게 입력했는지 검증하는 기능
    private int validateYearFormat(int year) {
        if(!FormatUtil.isYear(Integer.toString(year)))
            throw new MyInadequateFormatException();
        return year;
    }
    // 카드 결제 수단을 추가하는 과정에서 만료기간 중 달를 형식에 맞게 입력했는지 검증하는 기능
    private int validateMonthFormat(int month) {
        if(!FormatUtil.isMonth(month))
            throw new MyInadequateFormatException();
        return month;
    }

    // 카드 결제 수단을 추가하는 과정에서 입력한 달과 연을 통해서 저장하기 위한 LocalDate 객체를 생성하는 기능
    private LocalDate createExpireDate(int month, int year) {
        String mm = month < 10 ? CommonConstants.ZERO+month : String.valueOf(month);
        String date = CustomerViewLogicConstants.DAY_EX+mm+ CommonConstants.SLASH+year;
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(CustomerViewLogicConstants.DATE_FORMAT));
    }
    // 카드 결제 수단 추가 과정에서 카드 번호 형식에 대해서 검증하는 기능
    private String validateCardNoFormat(String cardNo) {
        if(!FormatUtil.isCardNo(cardNo))
            throw new MyInadequateFormatException();
        return cardNo;
    }
    // 카드 결제 수단 추가 과정에서 CVC 번호 형식에 대해서 검증하는 기능
    private String validateCVCFormat(String cvc) {
        if(!FormatUtil.isCVC(cvc))
            throw new MyInadequateFormatException();
        return cvc;
    }



    // 계좌 결제 수단을 추가하는 기능
    private void createAccount() {
        PaymentDto account = new PaymentDto();
        loop: while (true) {
            try{
                System.out.println(CustomerViewLogicConstants.REGISTER_ACCOUNT);
                System.out.println(CustomerViewLogicConstants.SELECT_BANK);
                BankType bankType = BankUtil.selectBankType(br);
                if(bankType==null)
                    return;
                while (true) {
                    try {
                        System.out.println(CustomerViewLogicConstants.showAccountNoEX(bankType.getFormat()));
                        System.out.print(CommonConstants.INPUT);
                        String command = sc.next();
                        if (command.equals(CommonConstants.ZERO)) {
                            continue loop;
                        }
                        String accountNo = BankUtil.checkAccountFormat(bankType,command);


                        account.setBankType(bankType)
                                .setAccountNo(accountNo)
                                .setCustomerId(this.customer.getId())
                                .setPayType(PayType.ACCOUNT);
                        break;
                    } catch (MyInadequateFormatException e) {
                        System.out.println(e.getMessage());
                    }
                }

                while (true) {
                    System.out.println(CustomerViewLogicConstants.REGISTER_ACCOUNT_INFO);
                    System.out.print(CommonConstants.INPUT);
                    String result = sc.next();
                    result = result.toUpperCase();
                    if (result.equals(CommonConstants.NO)) {
                        System.out.println(CustomerViewLogicConstants.CANCEL_REGISTER_PAYMENT);
                        return;
                    } else if (result.equals(CommonConstants.YES))
                        break;
                    else
                        throw new InputInvalidDataException();
                }

                break;
            }catch (ArrayIndexOutOfBoundsException | NumberFormatException| MyInadequateFormatException e) {
                System.out.println(ExceptionConstants.INPUT_WRONG_FORMAT);
            }
        }
        customer.addPayment(account);
        System.out.println(CustomerViewLogicConstants.SUCCESS_REGISTER_PAYMENT);
    }





    private Accident selectAccident() {
        Accident retAccident = null;
        int accidentId = 0;
        while (true) {
            accidentDao = new AccidentDaoImpl();
            List<Accident> accidents = accidentDao.readAllByCustomerId(customer.getId());
            System.out.println(CustomerViewLogicConstants.ACCIDENT_LIST);
            for (Accident accident : accidents) {
                accident.printForCustomer();
            }
            try {

                System.out.println(CommonConstants.ZERO_MESSAGE);
                System.out.println(CommonConstants.EXIT_MESSAGE);
                accidentId = (int) br.verifyRead(CustomerViewLogicConstants.INPUT_ACCIDENT_ID, accidentId);

                if (accidentId == 0) {
                    break;
                }
                accidentDao = new AccidentDaoImpl();
                retAccident = accidentDao.read(accidentId);
                if(retAccident.getCustomerId() != this.customer.getId())
                    throw new MyIllegalArgumentException(ExceptionConstants.INPUT_DATA_ON_LIST);
                break;
            } catch (InputException | MyIllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        if (accidentId != 0) {
            try{
                accidentDocumentFileDao = new AccidentDocumentFileDaoImpl();
                List<AccidentDocumentFile> files = accidentDocumentFileDao.readAllByAccidentId(retAccident.getId());
                for (AccidentDocumentFile file : files) {
                    retAccident.getAccDocFileList().put(file.getType(),file);
                }
            }catch (MyIllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        return retAccident;
    }

    private void reportAccident()  {
        AccidentType accidentType = selectAccidentType();
        if (accidentType != null)
            inputAccidentInfo(accidentType);
    }

    private void inputAccidentInfo(AccidentType selectAccidentType) {

        AccidentReportDto accidentReportDto = inputDetailAccidentInfo(inputCommonAccidentInfo(selectAccidentType));
        Accident accident = customer.reportAccident(accidentReportDto);
        accident.printForCustomer();

        AccidentType accidentType = accident.getAccidentType();
        if (accidentType == AccidentType.CARACCIDENT) {
            if (((CarAccident) accident).isRequestOnSite()) {
                CarAccidentService.connectWorker();
            }
        } else if (accidentType == AccidentType.CARBREAKDOWN) {
            CarAccidentService.connectWorker();
        }

    }

    private AccidentReportDto inputDetailAccidentInfo(AccidentReportDto accidentReportDto) {
        AccidentType accidentType = accidentReportDto.getAccidentType();
        return switch (accidentType) {
            case INJURYACCIDENT ->inputInjuryAccidentInfo(accidentReportDto);
            case CARBREAKDOWN -> inputCarBreakdown(accidentReportDto);
            case CARACCIDENT -> inputCarAccident(accidentReportDto);
            case FIREACCIDENT -> inputFireAccidentInfo(accidentReportDto);
        };
    }

    private AccidentReportDto inputPlaceAddress(AccidentReportDto accidentReportDto) {
        String placeAddress = "";
        placeAddress= (String) br.verifyRead(CustomerViewLogicConstants.ADDRESS, placeAddress);
        return accidentReportDto.setPlaceAddress(placeAddress);
    }

    private AccidentReportDto inputCarNo(AccidentReportDto accidentReportDto) {
        String carNo = "";
        while (true) {
            try {

                carNo = (String) br.verifyRead(CustomerViewLogicConstants.CAR_NO_EX, carNo);
                if (FormatUtil.isCarNo(carNo))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return accidentReportDto.setCarNo(carNo);
    }

    private AccidentReportDto inputCarAccident(AccidentReportDto accidentReportDto) {
        inputPlaceAddress(accidentReportDto);
        inputCarNo(accidentReportDto);

        String opposingDriverPhone = "";
        while (true) {
            try{

                opposingDriverPhone= (String) br.verifyRead(CustomerViewLogicConstants.OPOSSING_PHONE, opposingDriverPhone);
                if(FormatUtil.isPhone(opposingDriverPhone))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        accidentReportDto.setOpposingDriverPhone(opposingDriverPhone);


        String isRequestOnSite = "";
        while (true) {
            isRequestOnSite= (String) br.verifyRead(CustomerViewLogicConstants.REQUEST_ON_SITE, isRequestOnSite);
            isRequestOnSite = isRequestOnSite.toUpperCase();
            if(isRequestOnSite.equals(CommonConstants.YES)||isRequestOnSite.equals(CommonConstants.NO))
                break;
        }
        boolean request = false;
        if (isRequestOnSite.equals(CommonConstants.YES)) {
            request = true;
        }

        return accidentReportDto.setRequestOnSite(request);
    }

    private AccidentReportDto inputCarBreakdown(AccidentReportDto accidentReportDto) {
        inputPlaceAddress(accidentReportDto);
        inputCarNo(accidentReportDto);

        String symptom = "";
        symptom= (String) br.verifyRead(CustomerViewLogicConstants.SYMPTOM, symptom);
        return accidentReportDto.setSymptom(symptom);
    }

    private AccidentReportDto inputInjuryAccidentInfo(AccidentReportDto accidentReportDto) {
        String injurySite = "";

        injurySite = (String)br.verifyRead(CustomerViewLogicConstants.INJURY_SITE,injurySite);
        return accidentReportDto.setInjurySite(injurySite);
    }

    private AccidentReportDto inputFireAccidentInfo(AccidentReportDto accidentReportDto) {
        return inputPlaceAddress(accidentReportDto);
    }

    private AccidentReportDto inputCommonAccidentInfo(AccidentType selectAccidentType) {

        int year = 0;
        int month = 0;
        int day = 0;
        int  hour = 0;
        int min = 0;
        System.out.println(CustomerViewLogicConstants.REPORT_ACCIDENT_INFO);
        System.out.println(CustomerViewLogicConstants.INPUT_ACCIDENT_DATE);

        year = validateYear(year);
        month = vadliateMonth(month);
        day = validateDay(day);

        hour = validateHour(hour);
        min = validateMinute(min);

        String M = dateFormatter(month);
        String d = dateFormatter(day);
        String h = dateFormatter(hour);
        String m = dateFormatter(min);

        String dateFormat = year+ CommonConstants.SLASH+M+ CommonConstants.SLASH+d+ CommonConstants.SPACE+h+ CommonConstants.COLON+m;

        LocalDateTime accidentDate = LocalDateTime.parse(dateFormat, DateTimeFormatter.ofPattern(CustomerViewLogicConstants.DATE_FORMAT_HOUR_MINUTE));

        return new AccidentReportDto().setAccidentType(selectAccidentType)
                .setDateOfAccident(accidentDate)
                .setDateOfReport(LocalDateTime.now());
    }

    private int validateYear(int year) {
        while (true) {
            try {
                year = (int) br.verifyRead(CustomerViewLogicConstants.YEAR_EX, year);
                if (FormatUtil.isYear(Integer.toString(year)))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return year;
    }

    private int vadliateMonth(int month) {
        while (true) {
            try{

                month = (int) br.verifyRead(CustomerViewLogicConstants.MONTH, month);
                if(FormatUtil.isMonth(month))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            }catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return month;
    }

    private int validateDay(int day) {
        while (true) {
            try {
                day = (int) br.verifyRead(CustomerViewLogicConstants.DAY, day);
                if(FormatUtil.isDay(day))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }

        }
        return day;
    }

    private int validateHour(int hour) {
        while (true) {
            try {

                hour = (int) br.verifyRead(CustomerViewLogicConstants.HOUR, hour);
                if (FormatUtil.isHour(hour))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return hour;
    }

    private int validateMinute(int min) {
        while (true) {
            try{

                min = (int) br.verifyRead(CustomerViewLogicConstants.MINUTE, min);
                if(FormatUtil.isMinute(min))
                    break;
                throw new MyInadequateFormatException(ExceptionConstants.INPUT_WRONG_FORMAT);
            } catch (MyInadequateFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return min;
    }

    private String dateFormatter(int time) {
        String date = Integer.toString(time);
        if (time < 10) {
            date = CommonConstants.ZERO+time;
        }
        return date;
    }

    private AccidentType selectAccidentType() {
        contractList = new ContractDaoImpl();
        List<ContractwithTypeDto> contractTypes = contractList.findAllContractWithTypeByCustomerId(this.customer.getId());
        AccidentType accidentType = null;
            while (true) {
                try {
                    int insType = 0;
                    String query = MenuUtil.createMenuAndClose(CustomerViewLogicConstants.ACCIDENT_MENU, CustomerViewLogicConstants.KIND_OF_ACCIDENT);
                    insType = br.verifyMenu(query, CustomerViewLogicConstants.KIND_OF_ACCIDENT.length);

                    switch (insType) {
                        case 1 -> accidentType = AccidentType.CARACCIDENT;
                        case 2 -> accidentType = AccidentType.CARBREAKDOWN;
                        case 3 -> accidentType = AccidentType.INJURYACCIDENT;
                        case 4 -> accidentType = AccidentType.FIREACCIDENT;
                        case 0 -> accidentType = null;
                    }

                    if (accidentType == null)
                        break;

                    for (ContractwithTypeDto contractType : contractTypes) {
                        if(isValidateReportAccident(accidentType,contractType.getInsuranceType()))
                            return accidentType;
                    }
                    throw new MyInvalidAccessException(ExceptionConstants.NO_INSURANCE_ABOUT_ACCIDENT);
                } catch (MyInvalidAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        return accidentType;
    }

    private boolean isValidateReportAccident(AccidentType accidentType, InsuranceType insuranceType) {
        return switch (accidentType) {
            case CARACCIDENT, CARBREAKDOWN -> insuranceType == InsuranceType.CAR;
            case FIREACCIDENT -> insuranceType == InsuranceType.FIRE;
            case INJURYACCIDENT -> insuranceType == InsuranceType.HEALTH;
        };
    }


    private void claimCompensation() {
        try {
            Accident accident = selectAccident();
            if (accident == null)
                return;
            showRequiredDocFile(accident);
        } catch (MyIllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showRequiredDocFile(Accident accident) {
        AccidentType accidentType = accident.getAccidentType();
        switch (accidentType) {
            case CARACCIDENT -> showCarAccidentDoc(accident);
            case FIREACCIDENT -> showFireAccidentDoc(accident);
            case INJURYACCIDENT ->showInjuryAccidentDoc(accident);
            case CARBREAKDOWN -> throw new MyIllegalArgumentException(ExceptionConstants.CAR_BREAKDOWN_EXCEPTION);
        }
    }
    private void showCommonAccidentDoc(Accident accident) {

        submitDocFile(accident, AccDocType.CLAIMCOMP);
    }

    private void submitMedicalConfirmation(Accident accident) {
        submitDocFile(accident, AccDocType.MEDICALCERTIFICATION); // 진단서 제출
        submitDocFile(accident, AccDocType.CONFIRMADMISSIONDISCHARGE); // 입퇴원 확인서 제출
    }

    private void submitFile(Accident accident, AccDocType accDocType) {
        while (true) {
            try {
                String uploadMedicalCertification = "";
                FileDialogUtil.isExist(accident,accDocType);
                uploadMedicalCertification = (String) br.verifyRead(CustomerViewLogicConstants.getSubmitDocQuery(accDocType.getDesc()),uploadMedicalCertification);
                if (uploadMedicalCertification.equals(CommonConstants.YES)) {
                    AccidentDocumentFile accidentDocumentFile = customer.claimCompensation(accident, new AccidentDocumentFile().setAccidentId(accident.getId())
                            .setType(accDocType));
                    if (accidentDocumentFile == null) {
                        System.out.println(CustomerViewLogicConstants.getSubmitDocCancel(accDocType.getDesc()));
                        break;
                    }
                    break;
                } else if (uploadMedicalCertification.equals(CommonConstants.NO)) {
                    break;
                }
            } catch (MyFileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void submitDocFile(Accident accident, AccDocType accDocType) {
        System.out.println(CustomerViewLogicConstants.getSubmitDoc(accDocType.getDesc()));

        while (true) {
            try {
                String medicalCertification = "";
                medicalCertification = (String) br.verifyRead(CustomerViewLogicConstants.getDownloadDocExQuery(accDocType.getDesc()), medicalCertification);
                if (medicalCertification.equals(CommonConstants.YES)) {
                    String dir = CustomerViewLogicConstants.getExDirectory(accDocType.getDesc());
                    FileDialogUtil.download(dir);
                    break;
                } else if (medicalCertification.equals(CommonConstants.NO)) {
                    break;
                }
            } catch (MyFileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        submitFile(accident,accDocType);
    }

    private void showCarAccidentDoc(Accident accident) {
        showCommonAccidentDoc(accident);
        submitMedicalConfirmation(accident);
        submitDocFile(accident,AccDocType.CARACCIDENTFACTCONFIRMATION); // 교통사고 사실 확인원
        submitDocFile(accident,AccDocType.PAYMENTRESOLUTION); // 자동차 보험금 지급 결의서

        boolean submitted = isAllDocSubmitted(accident, AccDocType.CLAIMCOMP, AccDocType.MEDICALCERTIFICATION, AccDocType.CONFIRMADMISSIONDISCHARGE
                , AccDocType.CARACCIDENTFACTCONFIRMATION, AccDocType.PAYMENTRESOLUTION);

        isFinishedClaimComp(accident, submitted);
    }

    private void isFinishedClaimComp(Accident accident, boolean submitted) {

        if (submitted) {
            connectCompEmployee(accident);
        } else {
            System.out.println(CustomerViewLogicConstants.FAIL_TO_FINISH_CLAIM_COMPENSATION);
        }
    }

    private void connectCompEmployee(Accident accident) {

        Employee compEmployee = CompAssignUtil.assignCompEmployee();
        System.out.println(compEmployee.print());


        while (true) {
            String rtVal = "";
            rtVal = (String) br.verifyRead(CustomerViewLogicConstants.CHANGE_COMP_QUERY,rtVal);
            if (rtVal.equals(CommonConstants.YES)) {
                String reasons = "";
                reasons=(String)br.verifyRead(CustomerViewLogicConstants.INPUT_COMPLAIN,reasons);
                compEmployee = this.customer.changeCompEmp(reasons,compEmployee);
                System.out.println(compEmployee.print());
                System.out.println(CustomerViewLogicConstants.SUCCESS_CHANGE_COMP_EMPLOYEE);
                break;
            }else if(rtVal.equals(CommonConstants.NO)){
                break;
            }
        }
        accident.setEmployeeId(compEmployee.getId());
        accidentDao = new AccidentDaoImpl();
        accidentDao.updateCompEmployeeId(accident);
    }

    private void showFireAccidentDoc(Accident accident) {
        showCommonAccidentDoc(accident);
        submitFile(accident, AccDocType.PICTUREOFSITE); // 사고현장사진
        submitDocFile(accident, AccDocType.REPAIRESTIMATE); // 수리비 견적서
        submitDocFile(accident,AccDocType.REPAIRRECEIPT); // 수리비 영수증

        boolean submitted = isAllDocSubmitted(accident, AccDocType.CLAIMCOMP, AccDocType.PICTUREOFSITE, AccDocType.REPAIRESTIMATE, AccDocType.REPAIRRECEIPT);
        isFinishedClaimComp(accident, submitted);
    }

    private void showInjuryAccidentDoc(Accident accident) {
        showCommonAccidentDoc(accident);
        submitMedicalConfirmation(accident);

        boolean submitted = isAllDocSubmitted(accident, AccDocType.CLAIMCOMP, AccDocType.MEDICALCERTIFICATION, AccDocType.CONFIRMADMISSIONDISCHARGE);
        isFinishedClaimComp(accident, submitted);
    }

    private boolean isAllDocSubmitted(Accident accident, AccDocType ... accDocTypes) {
        Map<AccDocType, AccidentDocumentFile> accDocFileList = accident.getAccDocFileList();
        for (AccDocType accDocType : accDocTypes) {
            if (!accDocFileList.containsKey(accDocType)) {
                return false;
            }
        }
        return true;
    }


    public void payLogicforTest(Contract contract) {
        payLogic(contract);
    }
}
