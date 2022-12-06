package com.mju.insuranceCompany.service.insurance.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Accessors(chain = true)
public class InsuranceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_detail_id")
    protected int id;
    protected int premium;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    protected Insurance insurance;

}
