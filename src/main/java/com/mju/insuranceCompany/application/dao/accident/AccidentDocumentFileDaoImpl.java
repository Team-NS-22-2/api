package com.mju.insuranceCompany.application.dao.accident;

import com.mju.insuranceCompany.application.dao.Dao;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.application.global.exception.MyIllegalArgumentException;
import com.mju.insuranceCompany.application.global.utility.ConsoleColors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName :  dao
 * fileName : AccDocFileDao
 * author :  규현
 * date : 2022-05-30
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-30                규현             최초 생성
 */
public class AccidentDocumentFileDaoImpl extends Dao implements AccidentDocumentFileDao {

    public AccidentDocumentFileDaoImpl() {
        super.connect();
    }

    @Override
    public void create(AccidentDocumentFile accidentDocumentFile) {
        String query = "insert into accident_document_file (type, file_address, accident_id, last_modified_date)" +
                "values('%s', '%s', %d, '%s')";
        LocalDateTime now = LocalDateTime.now();
        String formattedQuery = String.format(query, accidentDocumentFile.getType().name(),
                accidentDocumentFile.getFileAddress(), accidentDocumentFile.getAccidentId(), now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        int id = super.create(formattedQuery);
        accidentDocumentFile.setId(id);
        accidentDocumentFile.setLastModifedDate(now);

    }

    @Override
    public AccidentDocumentFile read(int id) {
        String query = "select * from accident_document_file where accident_document_file_id = %d";
        String formattedQuery = String.format(query,id);
        ResultSet rs = super.read(formattedQuery);
        AccidentDocumentFile accidentDocumentFile = null;
        try {
            if (rs.next()) {
                accidentDocumentFile = new AccidentDocumentFile();
                accidentDocumentFile.setId(rs.getInt("accident_document_file_id"))
                        .setFileAddress(rs.getString("file_address"))
                        .setAccidentId(rs.getInt("accident_id"))
                        .setType(AccDocType.valueOf(rs.getString("type").toUpperCase()))
                        .setLastModifedDate(rs.getTimestamp("last_modified_date").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(accidentDocumentFile ==null)
            throw new MyIllegalArgumentException(ConsoleColors.RED_BOLD+"사고 서류 아이디 ["+id+"]에 해당하는 사고 서류 파일 정보가 존재하지 않습니다."+ ConsoleColors.RESET);
        return accidentDocumentFile;
    }




    @Override
    public boolean update(int id) {
        String query = "update accident_document_file set last_modified_date = '%s' where accident_document_file_id = %d";
        String formattedQuery = String.format(query, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),id);
        super.update(formattedQuery);

        return true;
    }

    @Override
    public List<AccidentDocumentFile> readAllByAccidentId(int accidentId) {
        String query = "select * from accident_document_file where accident_id = %d";
        String formattedQuery = String.format(query,accidentId);
        ResultSet rs = super.read(formattedQuery);
        List<AccidentDocumentFile> accidentDocumentFileList = new ArrayList<>();

            try {
                while(rs.next()) {
                    AccidentDocumentFile accidentDocumentFile = new AccidentDocumentFile();
                    accidentDocumentFile.setId(rs.getInt("accident_document_file_id"))
                            .setFileAddress(rs.getString("file_address"))
                            .setAccidentId(rs.getInt("accident_id"))
                            .setType(AccDocType.valueOf(rs.getString("type").toUpperCase()))
                            .setLastModifedDate(rs.getTimestamp("last_modified_date").toLocalDateTime());
                    accidentDocumentFileList.add(accidentDocumentFile);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(accidentDocumentFileList.isEmpty())
            throw new MyIllegalArgumentException(ConsoleColors.YELLOW_BOLD+"[알림] 사고 아이디 ["+accidentId+"]에 해당하는 사고 파일 정보가 존재하지 않습니다."+ ConsoleColors.RESET);
        return accidentDocumentFileList;
    }


    @Override
    public boolean delete(int id) {
        return false;
    }
}
