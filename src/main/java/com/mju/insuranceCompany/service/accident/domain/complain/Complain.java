package com.mju.insuranceCompany.service.accident.domain.complain;

import com.mju.insuranceCompany.service.accident.controller.dto.ComplainRequestDto;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complain_id")
    private int id;
    private int customerId;
    private String reason;

    public static Complain createComplain(ComplainRequestDto dto, int customerId) {
        return Complain.builder()
                .customerId(customerId)
                .reason(dto.getReason())
                .build();
    }

}
