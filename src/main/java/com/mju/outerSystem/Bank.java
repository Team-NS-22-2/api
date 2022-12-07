package com.mju.outerSystem;

/**
 * packageName :  outerSystem
 * fileName : Bank
 * author :  규현
 * date : 2022-05-25
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-25                규현             최초 생성
 */
public class Bank {

    private static long INSURANCE_COMPANY_BALANCE = 100000000000L;

    public static String pay(String bank, String accountNo, long amount) {
        if(INSURANCE_COMPANY_BALANCE < amount) {
            return "계좌의 잔액이 부족하여 송금에 실패하였습니다.";
        }
        INSURANCE_COMPANY_BALANCE -= amount;
        return accountNo +"("+ bank +")"+ "에 "+ amount +"원을 정상적으로 송금하였습니다.";
    }

}
