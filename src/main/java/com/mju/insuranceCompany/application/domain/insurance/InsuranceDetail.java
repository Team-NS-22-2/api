package com.mju.insuranceCompany.application.domain.insurance;

import com.mju.insuranceCompany.application.viewlogic.dto.insurance.response.InsuranceDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class InsuranceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_detail_id")
    private int id;
    private int premium;
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    public InsuranceDetailDto toInsuranceDetailDto() {
        return InsuranceDetailDto.builder()
                .id(this.getId())
                .premium(this.getPremium())
                .build();
    }

}
