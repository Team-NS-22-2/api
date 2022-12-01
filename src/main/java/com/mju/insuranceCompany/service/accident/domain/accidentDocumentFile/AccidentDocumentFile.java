package com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccidentDocumentFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accident_document_file_id")
	private int id;
	@Enumerated(value = EnumType.STRING)
	private AccDocType type;
	private String fileAddress;
	private int accidentId;
	private LocalDateTime lastModifiedDate;

	public static AccidentDocumentFile createAccidentDocumentFile(
			AccDocType docType, String fileAddress, int accidentId) {
		return AccidentDocumentFile.builder()
				.type(docType)
				.fileAddress(fileAddress)
				.accidentId(accidentId)
				.lastModifiedDate(LocalDateTime.now())
				.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccidentDocumentFile that = (AccidentDocumentFile) o;
		return Objects.equals(getFileAddress(), that.getFileAddress()) && getType() == that.getType();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getFileAddress(), getType());
	}
}