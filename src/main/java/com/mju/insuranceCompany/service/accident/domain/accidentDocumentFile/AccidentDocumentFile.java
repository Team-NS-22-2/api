package com.mju.insuranceCompany.service.accident.domain.accidentDocumentFile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccidentDocumentFile {

	private int accidentId;
	private String fileAddress;
	@Id
	private int id;
	private AccDocType type;
	private LocalDateTime lastModifedDate;

	@Override
	public String toString() {
		return "AccDocFile{" +
				"accidentId=" + accidentId +
				", fileAddress='" + fileAddress + '\'' +
				", id=" + id +
				", type=" + type +
				", lastModifedDate=" + lastModifedDate +
				'}';
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