package com.mju.insurancecompany.original.application.global.constant;

/**
 * packageName :  insuranceCompany.application.global.constants
 * fileName : ExceptionConstants
 * author :  규현
 * date : 2022-06-02
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-06-02                규현             최초 생성
 */
public class ExceptionConstants {

    //이미 값이 있는 예외클래스들
    public static final String INPUT_INVALID_MENU_EXCEPTION = "\033[1;31mERROR!! : 올바른 메뉴를 입력해주세요.\n\033[0m";
    public static final String CAR_BREAKDOWN_EXCEPTION ="\033[1;31mERROR!! : 자동차 고장은 보상금 청구가 되지 않습니다.\033[0m";
    public static final String INPUT_DATA_ON_LIST = "\033[1;31mERROR!! : 리스트에 있는 아이디를 입력해주세요.";
    public static final String NO_INSURANCE_ABOUT_ACCIDENT ="\033[1;31mERROR!! : 해당 사고를 접수하기 위한 보험에 가입되어있지 않습니다. 다시 확인해주세요.\033[0m";
    public static final String INPUT_WRONG_FORMAT = "\033[1;31mERROR!! : 정확한 형식의 값을 입력해주세요.\033[0m";
    public static final String FILE_NOT_FOUND = "\033[1;31mERROR :: 파일을 찾을 수 없습니다!\033[0m";

    public static String getFileNotFoundMessage(String format) {
        return "\033[1;31mERROR :: "+format+" 파일을 찾을 수 없습니다!\033[0m";
    }

}
