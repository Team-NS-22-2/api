package com.mju.insuranceCompany.application.domain.customer;


import com.mju.insuranceCompany.application.domain.accident.complain.Complain;
import com.mju.insuranceCompany.application.domain.contract.*;
import com.mju.insuranceCompany.application.domain.customer.payment.Payment;
import com.mju.insuranceCompany.application.domain.insurance.*;
import com.mju.insuranceCompany.application.global.exception.NoResultantException;
import com.mju.insuranceCompany.application.global.utility.CriterionSetUtil;
import com.mju.insuranceCompany.application.global.utility.TargetInfoCalculator;
import com.mju.insuranceCompany.application.login.User;
import com.mju.insuranceCompany.application.viewlogic.dto.contract.CarContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contract.ContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contract.FireContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contract.HealthContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.request.CustomerBasicRequest;
import com.mju.insuranceCompany.application.viewlogic.dto.customer.response.*;
import com.mju.insuranceCompany.application.viewlogic.dto.user.UserRequestDto;
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

	public int inquireHealthPremium(String ssn, int riskCount, List<InsuranceDetail> insuranceDetails){
		int premium = 0;
		int targetAge = CriterionSetUtil.setTargetAge((TargetInfoCalculator.targetAgeCalculator(ssn)));
		boolean targetSex = TargetInfoCalculator.targetSexCalculator(ssn);
		boolean riskCriterion = CriterionSetUtil.setRiskCriterion(riskCount);

//		List<InsuranceDetailDto> insuranceDetails = insurance.getInsuranceDetailList();
		for (InsuranceDetail insuranceDetail : insuranceDetails) {
			HealthDetail healthDetail = (HealthDetail) insuranceDetail;
			if (healthDetail.getTargetAge() == targetAge && healthDetail.isTargetSex() == targetSex && (healthDetail.isRiskCriterion()) == riskCriterion) {
				premium = healthDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public int inquireFirePremium(BuildingType buildingType, Long collateralAmount, List<InsuranceDetail> insuranceDetails){
		int premium = 0;
		Long collateralAmountCriterion = CriterionSetUtil.setCollateralAmountCriterion(collateralAmount);

//		List<FireDetailDto> insuranceDetails = insurance.getInsuranceDetailList();
		for (InsuranceDetail insuranceDetail : insuranceDetails) {
			FireDetail fireDetail = (FireDetail) insuranceDetail;
			if (fireDetail.getTargetBuildingType() == buildingType && fireDetail.getCollateralAmountCriterion() == collateralAmountCriterion) {
				premium = fireDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public int inquireCarPremium(String ssn, Long value, List<InsuranceDetail> insuranceDetails){
		int premium = 0;
		int targetAge = CriterionSetUtil.setTargetAge(TargetInfoCalculator.targetAgeCalculator(ssn));
		Long valueCriterion = CriterionSetUtil.setValueCriterion(value);

		for (InsuranceDetail insuranceDetail : insuranceDetails) {
			CarDetail carDetail = (CarDetail) insuranceDetail;
			if (carDetail.getTargetAge() == targetAge && carDetail.getValueCriterion() == valueCriterion) {
				premium = carDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public Customer registerCustomer(CustomerBasicRequest customerDto) {
		return Customer.builder()
				.name(customerDto.getName())
				.ssn(customerDto.getSsn())
				.address(customerDto.getAddress())
				.phone(customerDto.getPhone())
				.email(customerDto.getEmail())
				.job(customerDto.getJob())
				.build();
	}

	public User registerUser(UserRequestDto userDto) {
		return User.builder()
				.userId(userDto.getUserId())
				.password(userDto.getPassword())
				.roleId(userDto.getRoleId())
				.build();
	}

	public Contract registerContract(Customer customer, ContractDto contractDto, int insuranceId, InsuranceType insuranceType) {
		Contract contract = null;
		switch (insuranceType){
			case HEALTH -> {
				HealthContract healthContract = HealthContract.builder()
						.height(((HealthContractDto) contractDto).getHeight())
						.weight(((HealthContractDto) contractDto).getWeight())
						.isDrinking(((HealthContractDto) contractDto).isDrinking())
						.isSmoking(((HealthContractDto) contractDto).isSmoking())
						.isDriving(((HealthContractDto) contractDto).isDriving())
						.isDangerActivity(((HealthContractDto) contractDto).isDangerActivity())
						.isHavingDisease(((HealthContractDto) contractDto).isHavingDisease())
						.isTakingDrug(((HealthContractDto) contractDto).isTakingDrug())
						.diseaseDetail(((HealthContractDto) contractDto).getDiseaseDetail())
						.build();
				healthContract.setInsuranceId(insuranceId)
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

//				ContractDaoImpl contractDao = new ContractDaoImpl();
//				contractDao.create(healthContract);
				contract = healthContract;
			}
			case FIRE -> {
				FireContract fireContract = FireContract.builder()
						.buildingArea(((FireContractDto) contractDto).getBuildingArea())
						.buildingType(((FireContractDto) contractDto).getBuildingType())
						.collateralAmount(((FireContractDto) contractDto).getCollateralAmount())
						.isActualResidence(((FireContractDto) contractDto).isActualResidence())
						.isSelfOwned(((FireContractDto) contractDto).isSelfOwned())
						.build();
				fireContract.setInsuranceId(insuranceId)
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

//				ContractDaoImpl contractDao = new ContractDaoImpl();
//				contractDao.create(fireContract);
				contract = fireContract;
			}
			case CAR -> {
				CarContract carContract = CarContract.builder()
						.carNo(((CarContractDto) contractDto).getCarNo())
						.carType(((CarContractDto) contractDto).getCarType())
						.modelName(((CarContractDto) contractDto).getModelName())
						.modelYear(((CarContractDto) contractDto).getModelYear())
						.value(((CarContractDto) contractDto).getValue())
						.build();
				carContract.setInsuranceId(insuranceId)
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

//				ContractDaoImpl contractDao = new ContractDaoImpl();
//				contractDao.create(carContract);
				contract = carContract;
			}
		}
		return contract;
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