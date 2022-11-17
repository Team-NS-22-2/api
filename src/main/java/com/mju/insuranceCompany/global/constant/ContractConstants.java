package com.mju.insuranceCompany.global.constant;

import static com.mju.insuranceCompany.global.utility.ConsoleColors.RED_BOLD;
import static com.mju.insuranceCompany.global.utility.ConsoleColors.RESET;

public class ContractConstants {
    // Sales Menu
    public static final String SALES_MENU = "<<영업팀 메뉴>>";
    public static final String[] SALES_MENU_ELEMENTS = {"보험상품설계"};

    public static final String [] MENU_ELEMENT_GUEST_VIEW_LOGIC = SALES_MENU_ELEMENTS;

    // selectInsurance()
    public static final String CONTRACT_INSURANCE_LIST = "<< 보험상품목록 >>";
    public static final String CONTRACT_INSURANCE_ID = "상품번호";
    public static final String CONTRACT_INSURANCE_NAME = "보험이름";
    public static final String CONTRACT_INSURANCE_TYPE = "보험종류";
    public static final String CONTRACT_INPUT_INSURANCE_ID = "상품번호: ";
    public static final String CONTRACT_INSURANCE_DESCRIPTION = "<< 상품안내 >>\n";
    public static final String CONTRACT_INSURANCE_CONTRACT_PERIOD = "\n계약기간: ";
    public static final String CONTRACT_INSURANCE_PAYMENT_PERIOD = "납입기간: ";
    public static final String CONTRACT_INSURANCE_GUARANTEE = "\n<< 보장내역 >>";
    public static final String CONTRACT_INSURANCE_GUARANTEE_DESCRIPTION = "보장내용";
    public static final String CONTRACT_INSURANCE_GUARANTEE_AMOUNT = "보장금액";
    public static final String CONTRACT_YEAR_PARAMETER = "년 ";

    public static final String CUSTOMER_SELECT_INSURANCE_ID = "가입할 보험상품의 번호를 입력하세요. \t(0: 뒤로가기)";
    public static final String SALES_SELECT_INSURANCE_ID = "설계할 보험상품의 번호를 입력하세요. \t(0 : 뒤로가기)";

    // progressContract()
    public static final String SALES_INQUIRE_CONDITION = "가입조건을 확인하시겠습니까? \n1. 예  2. 아니요\n";
    public static final String SALES_BACK = "(0 : 뒤로가기)\n";
    public static final String SALES_INSURANCE_DATAIL = "<< 가입조건 >>";
    public static final String SALES_TARGET_AGE = "나이";
    public static final String SALES_TARGET_SEX = "성별";
    public static final String SALES_RISK_CRITERTION = "고위험군 여부";
    public static final String SALES_BUIILDING_TYPE = "건물종류";
    public static final String SALES_COLLATERAL_AMOUNT_CRITERION = "담보금액기준";
    public static final String SALES_VALUE_CRITERION = "차량가액기준";

    public static final String SALES_PROGRESS_CONTRACT = "보험계약을 진행하시겠습니까?\n1. 계약  2. 취소\n";
    public static final String CUSTOMER_DICIDE_SIGNING = "해당 보험상품을 가입하시겠습니까?\n1. 가입  2. 취소\n";

    public static final String SALES__PREMIUM= "보험료";
    public static final String SALES_CANCEL= "계약이 취소되었습니다.";

    // Method plan...
    public static final String SALES_TARGET_AGE_QUERY = "고객님의 나이: ";
    public static String premiumInquiry(int premium) {
        return "<< 조회된 귀하의 보험료는 " + premium + "원 입니다. >>";
    }

    // Method inputCustomerInfo()
    public static final String SALES_IS_CONTRACTED_CUSTOMER = "등록된 회원입니까? (1. 예  2. 아니요)\n";
    public static final String SALES_INPUT_CUSTOMER_ID = "고객 ID를 입력해주세요.\n고객 ID: ";
    public static final String CONTRACT_INPUT_CUSTOMER_INFO = "<< 고객님의 개인정보를 입력해주세요. >>";
    public static final String CONTRACT_CUSTOMER_NAME_QUERY = "이름: ";
    public static final String CONTRACT_SSN_QUERY = "주민번호 (______-*******): ";
    public static final String CONTRACT_PHONE_QUERY = "연락처 (0__-____-____): ";
    public static final String CONTRACT_ADDRESS_QUERY = "주소: ";
    public static final String CONTRACT_EMAIL_QUERY = "이메일 (_____@_____.___): ";
    public static final String CONTRACT_JOB_QUERY = "직업: ";

    // inputHealthInfo()
    public static final String CONTRACT_INPUT_HEALTH_INFO = "<< 고객님의 건강정보를 입력해주세요. >>";
    public static final String CONTRACT_HEIGHT_QUERY = "키 (단위: cm): ";
    public static final String CONTRACT_WEGHIT_QUERY = "몸무게 (단위: kg): ";
    public static final String CONTRACT_TARGET_SEX_QUERY = "성별 (1. 남  2. 여)\n";
    public static final String CONTRACT_IS_DRINKING_QUERY = "음주 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_SMOKING_QUERY = "흡연 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_DRIVING_QUERY = "운전 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_DANGER_ACTIVITY_QUERY = "위험 취미 활동 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_TAKING_DRUG_QUERY = "약물 복용 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_HAVING_DISEASE_QUERY = "질병 이력 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_DISEASE_DETAIL_QUERY = "질병에 대한 상세 내용를 입력해주세요.\n";

    // inputFireInfo()
    public static final String CONTRACT_BUILDING_AREA_QUERY = "건물면적 (단위: m^2): ";
    public static final String CONTRACT_BUILDING_TYPE_QUERY = "건물종류를 선택해주세요.\n1. 상업용\n2. 산업용\n3. 기관용\n4. 거주용\n";
    public static final String CONTRACT_COLLATERAL_AMOUNT_QUERY = "담보금액: (단워: 원): ";
    public static final String CONTRACT_IS_SELF_OWNED_QUERY = "자가 여부를 입력해주세요. (1. 예  2. 아니요)\n";
    public static final String CONTRACT_IS_ACTUAL_RESIDENCE_QUERY = "실거주 여부를 입력해주세요. (1. 예  2. 아니요)\n";

    // inputCarInfo()
    public static final String CONTRACT_CAR_NO_QUERY = "차량번호 (***_ ****): ";
    public static final String CONTRACT_CAR_TYPE_QUERY = "차종을 선택해주세요.\n1. 경형\n2. 소형\n3. 준중형\n4. 중형\n5. 준대형\n6. 대형\n7. 스포츠카\n";
    public static final String CONTRACT_MADEL_NAME_QUERY = "모델 이름: ";
    public static final String CONTRACT_MODEL_YEAR_QUERY = "차량 연식 (단위: 년): ";
    public static final String CONTRACT_VALUE_QUERY = "차량가액 (단위: 원): ";

    // concludeContract()
    public static final String SALES_CONCLUDE_CONTRACT = "보험계약을 체결하시겠습니까?\n1. 계약체결  2. 취소\n";
    public static final String SALES_CONCLUDE = "계약을 체결하였습니다.";

    public static final String CUSTOMER_SGIN_CONTRACT = "보험가입을 신청하시겠습니까?\n1. 가입  2. 취소\n";
    public static final String CUSTOMER_SIGN = "가입이 완료되었습니다.";
    public static final String CUSTOMER_CANCEL = "가입이 취소되었습니다.";

    public static final String CONTRACT_SIGN_UP = "<< 회원가입 >>";
    public static final String CONTRACT_USER_ID_QUERY = "아이디: ";
    public static final String CONTRACT_USER_PASSWORD_QUERY = "비밀번호: ";

    // Table format
    public static final String CONTRACT_INSURANCES_CATEGORY_FORMAT = "%-4s\t|\t%-10s\t|\t%-5s\n";
    public static final String CONTRACT_INSURANCES_VALUE_FORMAT = "%-4s\t|\t%-10s\t|\t%-5s\n";
    public static final String CONTRACT_GUARANTEES_CATEGORY_FORMAT = "%-10s\t|\t%-10s\t|\t%-11s\n";
    public static final String CONTRACT_GUARANTEES_VALUE_FORMAT = "%-10s\t|\t%-10s\t|\t%-5d원\n";
    public static final String CONTRACT_HEALTH_DETAIL_CATEGORY_FORMAT = "%-8s\t|\t%-3s\t|\t%-7s\t|\t%-8s\n";
    public static final String CONTRACT_HEALTH_DETAIL_VALUE_FORMAT = "%-8s\t|\t%-3s\t|\t%-7s\t|\t%-5d원\n";
    public static final String CONTRACT_FIRE_DETAIL_CATEGORY_FORMAT = "%-2s\t|\t%-10s\t|\t%-7s\n";
    public static final String CONTRACT_FIRE_DETAIL_VALUE_FORMAT = "%-3s\t|\t%-10s\t|\t%-7d원\n";
    public static final String CONTRACT_CAR_DETAIL_CATEGORY_FORMAT = "%-8s\t|\t%-20s\t|\t%-5s\n";
    public static final String CONTRACT_CAR_DETAIL_VALUE_FORMAT = "%-8s\t|\t%-20s\t|\t%-5d원\n";
    public static final String CONTRACT_SHORT_DIVISION = "________________________________________";
    public static final String CONTRACT_LONG_DIVISION = "____________________________________________________";

    // exceptions
    public static String exceptionNoInsurance(int insuranceID){return RED_BOLD+"ERROR:: ID["+ insuranceID + "]에 해당하는 보험 정보가 존재하지 않습니다."+RESET;}
}
