package com.mju.insuranceCompany.application.dao.accident;

import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;

import java.util.List;

/**
 * packageName :  main.domain.accident.accDocFile
 * fileName : AccDocFileList
 * author :  규현
 * date : 2022-05-18
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-18                규현             최초 생성
 */
public interface AccidentDocumentFileDao extends CrudInterface<AccidentDocumentFile> {

    List<AccidentDocumentFile> readAllByAccidentId(int accidentId);
}
