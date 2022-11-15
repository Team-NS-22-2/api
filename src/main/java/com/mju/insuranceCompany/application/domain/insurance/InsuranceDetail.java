package com.mju.insuranceCompany.application.domain.insurance;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class InsuranceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_detail_id")
    private int id;
    private int premium;
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

}