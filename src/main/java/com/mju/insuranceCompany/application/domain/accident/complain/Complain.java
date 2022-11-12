package com.mju.insuranceCompany.application.domain.accident.complain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName :  domain.complain
 * fileName : Complain
 * author :  규현
 * date : 2022-05-23
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-23                규현             최초 생성
 */
@Getter
@Setter
public class Complain {

    private int id;
    private int customerId;
    private String reason;

    @Builder
    public Complain(int customerId, String reason) {
        this.customerId = customerId;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Complain{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
