package com.mju.insuranceCompany.service.accident.domain.complain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Complain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
