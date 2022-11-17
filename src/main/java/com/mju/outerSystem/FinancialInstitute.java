package com.mju.outerSystem;

import com.mju.insuranceCompany.service.customer.domain.payment.Payment;

public interface FinancialInstitute {

    public static void validPaymentInfo(Payment payment) {
//        System.out.println(ConsoleColors.YELLOW_BOLD+"[알림] " + payment.toStringForValid() + " 는 사용가능합니다."+ ConsoleColors.RESET);
    }
}
