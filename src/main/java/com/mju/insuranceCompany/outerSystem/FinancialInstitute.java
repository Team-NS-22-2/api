package com.mju.insuranceCompany.outerSystem;

import com.mju.insuranceCompany.application.domain.customer.payment.Payment;
import com.mju.insuranceCompany.application.global.utility.ConsoleColors;

public interface FinancialInstitute {

    public static void validPaymentInfo(Payment payment) {
        System.out.println(ConsoleColors.YELLOW_BOLD+"[알림] " + payment.toStringForValid() + " 는 사용가능합니다."+ ConsoleColors.RESET);
    }
}
