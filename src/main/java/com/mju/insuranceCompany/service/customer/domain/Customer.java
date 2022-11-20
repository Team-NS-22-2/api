package com.mju.insuranceCompany.service.customer.domain;


import com.mju.insuranceCompany.service.accident.domain.complain.Complain;
import com.mju.insuranceCompany.service.customer.controller.dto.CustomerBasicDto;
import com.mju.insuranceCompany.service.customer.domain.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int id;
	private String name;
	private String ssn;
	private String address;
	private String phone;
	private String email;
	private String job;
	@OneToMany
	private List<Payment> paymentList;
	@OneToMany
	private List<Complain> complainList;

	public Customer(CustomerBasicDto dto) {
		name = dto.getName();
		ssn = dto.getSsn();
		address = dto.getAddress();
		phone = dto.getPhone();
		email = dto.getEmail();
		job = dto.getJob();
	}
	/*

	public Employee changeCompEmp(String reason, Employee compEmployee){
		Complain complain = Complain.builder().reason(reason)
				.customerId(this.id).build();

		this.complainList.add(complain);
		ComplainDao complainDao = new ComplainDaoImpl();
		complainDao.create(complain);

		return CompAssignUtil.changeCompEmployee(compEmployee);
	}

	// 파일을 선택해서 저장하고, 파일 주소를 리턴하는 식으로 해야할듯?
	public AccidentDocumentFile claimCompensation(Accident accident, AccidentDocumentFile accidentDocumentFile){

		String path = DocUtilConstants.getSubmitPath(id,accident.getId(),accidentDocumentFile.getType().getDesc());
		String extension = "";
		AccDocType accDocType = accidentDocumentFile.getType();
		if(accDocType == AccDocType.PICTUREOFSITE)
			extension = DocUtilConstants.JPG_EXTENSION;
		else
			extension = DocUtilConstants.HWP_EXTENSION;

		String directory = FileDialogUtil.uploadAccidentDocumentFile(path+extension);
		if (directory==null) {
			return null;
		}
		accidentDocumentFile.setFileAddress(directory);

		AccidentDocumentFileDaoImpl accidentDocumentFileList = new AccidentDocumentFileDaoImpl();
		if (accident.getAccDocFileList().containsKey(accDocType)) {
			accidentDocumentFileList.update(accident.getAccDocFileList().get(accDocType).getId());
		} else {
			accidentDocumentFileList.create(accidentDocumentFile);
			accident.getAccDocFileList().put(accDocType, accidentDocumentFile);
		}
		return accidentDocumentFile;
	}



	public void pay(Contract contract){
		PaymentDao paymentDao = new PaymentDaoImpl();
		Payment payment = paymentDao.read(contract.getPaymentId());
		if(payment != null)
			ElectronicPaymentSystem.pay(payment.toStringForPay(), contract.getPremium());
	}

	public List<Contract> readContracts(){
		ContractDao contractList = new ContractDaoImpl();
		return contractList.findAllByCustomerId(this.getId());
	}

	public void readPayments(){
		PaymentDao paymentDao = new PaymentDaoImpl();
		List<Payment> payments = paymentDao.findAllByCustomerId(this.id);
		this.setPaymentList((List<Payment>) payments);
	}

	private Payment createPayment(PaymentDto paymentDto) {
		PayType payType = paymentDto.getPayType();
		Payment payment;
		if (payType.equals(PayType.CARD)) {
			payment = Card.builder()
					.cardNo(paymentDto.getCardNo())
					.cvcNo(paymentDto.getCvcNo())
					.cardType(paymentDto.getCardType())
					.expiryDate(paymentDto.getExpiryDate())
					.build();
		} else {
			payment = Account.builder()
					.accountNo(paymentDto.getAccountNo())
					.bankType(paymentDto.getBankType())
					.build();
		}
		payment.setPaytype(payType);
		payment.setCustomerId(paymentDto.getCustomerId());
		return payment;

	}

	public void addPayment(PaymentDto paymentDto){
		Payment payment = createPayment(paymentDto);
		FinancialInstitute.validPaymentInfo(payment);
		PaymentDao paymentDao = new PaymentDaoImpl();
		paymentDao.create(payment);
		this.paymentList.add(payment);
	}

	public void registerPayment(Contract contract, int paymentId) {
		PaymentDao paymentDao = new PaymentDaoImpl();
		Payment payment = paymentDao.read(paymentId);
		if (payment.getCustomerId() != this.id) {
			throw new MyIllegalArgumentException(ExceptionConstants.INPUT_DATA_ON_LIST);
		}

		contract.setPaymentId(payment.getId());
		ContractDao contractList = new ContractDaoImpl();
		contractList.updatePayment(contract.getId(),payment.getId());
	}

	public Accident reportAccident(AccidentReportDto accidentReportDto){
		AccidentType accidentType = accidentReportDto.getAccidentType();

		Accident accident = null;
//		if (accidentType == AccidentType.INJURYACCIDENT) {
//			accident = new InjuryAccident();
//			((InjuryAccident) accident).setInjurySite(accidentReportDto.getInjurySite());
//		} else if(accidentType == AccidentType.CARACCIDENT) {
//			accident = new CarAccident();
//			((CarAccident)accident).setCarNo(accidentReportDto.getCarNo())
//					.setPlaceAddress(accidentReportDto.getPlaceAddress())
//					.setOpposingDriverPhone(accidentReportDto.getOpposingDriverPhone())
//					.setRequestOnSite(accidentReportDto.isRequestOnSite());
//		} else if (accidentType == AccidentType.CARBREAKDOWN) {
//			accident = new CarBreakdown();
//			((CarBreakdown) accident).setCarNo(accidentReportDto.getCarNo())
//					.setPlaceAddress(accidentReportDto.getPlaceAddress())
//					.setSymptom(accidentReportDto.getSymptom());
//		} else {
//			accident = new FireAccident();
//			((FireAccident)accident).setPlaceAddress(accidentReportDto.getPlaceAddress());
//		}
//		 accident.setAccidentType(accidentType)
//				.setDateOfAccident(accidentReportDto.getDateOfAccident())
//				.setDateOfReport(accidentReportDto.getDateOfReport())
//				.setCustomerId(this.id);
//
//		AccidentDao accidentDao = new AccidentDaoImpl();
//		accidentDao.create(accident);
		return accident;
	}

	 */
	public void signUp() {
	}

	public void terminate(){
	}

}