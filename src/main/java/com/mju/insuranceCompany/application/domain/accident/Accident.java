package com.mju.insuranceCompany.application.domain.accident;

import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class Accident {


//	protected Map<AccDocType, AccidentDocumentFile> accDocFileList = new HashMap<>();
	protected AccidentType accidentType;
	protected int customerId;
	protected LocalDateTime dateOfAccident;
	protected LocalDateTime dateOfReport;
	protected int employeeId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accident_id")
	protected int id;
	protected long lossReserves; // 지급준비금

}