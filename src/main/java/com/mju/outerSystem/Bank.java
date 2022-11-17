package com.mju.outerSystem;

import com.mju.insuranceCompany.service.customer.domain.payment.Account;

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
public interface Bank {

    public static void sendCompensation(Account account, long compensation) {
//        System.out.println(ConsoleColors.YELLOW_BOLD+"[알림] ["+account.getBankType() + "]"+account.getAccountNo() + "로 "+compensation + "원이 지급되었습니다."+ ConsoleColors.RESET);
    }
}
