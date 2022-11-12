package com.mju.insuranceCompany.application.dao.accident;

import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.domain.accident.complain.Complain;

import java.util.List;

/**
 * packageName :  domain.complain
 * fileName : ComplainList
 * author :  규현
 * date : 2022-05-23
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-23                규현             최초 생성
 */
public interface ComplainDao extends CrudInterface<Complain> {
    List<Complain> readAllByCustomerId(int customerId);
}
