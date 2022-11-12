package com.mju.insuranceCompany.application.domain.customer;


import com.mju.insuranceCompany.application.dao.accident.*;
import com.mju.insuranceCompany.application.dao.contract.ContractDao;
import com.mju.insuranceCompany.application.dao.contract.ContractDaoImpl;
import com.mju.insuranceCompany.application.dao.customer.CustomerDaoImpl;
import com.mju.insuranceCompany.application.dao.customer.PaymentDao;
import com.mju.insuranceCompany.application.dao.customer.PaymentDaoImpl;
import com.mju.insuranceCompany.application.dao.insurance.InsuranceDaoImpl;
import com.mju.insuranceCompany.application.dao.user.UserDaoImpl;
import com.mju.insuranceCompany.application.domain.accident.*;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccDocType;
import com.mju.insuranceCompany.application.domain.accident.accidentDocumentFile.AccidentDocumentFile;
import com.mju.insuranceCompany.application.domain.accident.complain.Complain;
import com.mju.insuranceCompany.application.domain.contract.*;
import com.mju.insuranceCompany.application.domain.customer.payment.*;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.domain.insurance.*;
import com.mju.insuranceCompany.application.global.constant.DocUtilConstants;
import com.mju.insuranceCompany.application.global.constant.ExceptionConstants;
import com.mju.insuranceCompany.application.global.exception.MyIllegalArgumentException;
import com.mju.insuranceCompany.application.global.exception.NoResultantException;
import com.mju.insuranceCompany.application.global.utility.CompAssignUtil;
import com.mju.insuranceCompany.application.global.utility.CriterionSetUtil;
import com.mju.insuranceCompany.application.global.utility.FileDialogUtil;
import com.mju.insuranceCompany.application.global.utility.TargetInfoCalculator;
import com.mju.insuranceCompany.application.login.User;
import com.mju.insuranceCompany.application.viewlogic.dto.UserDto.UserDto;
import com.mju.insuranceCompany.application.viewlogic.dto.accidentDto.AccidentReportDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.CarContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.ContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.FireContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.contractDto.HealthContractDto;
import com.mju.insuranceCompany.application.viewlogic.dto.customerDto.CustomerDto;
import com.mju.insuranceCompany.outerSystem.ElectronicPaymentSystem;
import com.mju.insuranceCompany.outerSystem.FinancialInstitute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 규현
 * @version 1.0
 * @created 09-5-2022 오전 2:42:24
 */
public class Customer {

	private int id;
	private String name;
	private String ssn;
	private String address;
	private String phone;
	private String email;
	private String job;
	private ArrayList<Payment> paymentList = new ArrayList<>();
	private ArrayList<Complain> complainList = new ArrayList<>();

	public ArrayList<Complain> getComplainList() {
		return complainList;
	}

	public Customer setComplainList(ArrayList<Complain> complainList) {
		this.complainList = complainList;
		return this;
	}

	public ArrayList<Payment> getPaymentList() {
		return paymentList;
	}

	public Customer setPaymentList(ArrayList<Payment> paymentList) {
		this.paymentList = paymentList;
		return this;
	}
	public String getAddress() {
		return address;
	}

	public Customer setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Customer setEmail(String email) {
		this.email = email;
		return this;
	}

	public int getId() {
		return id;
	}

	public Customer setId(int id) {
		this.id = id;
		return this;
	}

	public String getJob() {
		return job;
	}

	public Customer setJob(String job) {
		this.job = job;
		return this;
	}

	public String getName() {
		return name;
	}

	public Customer setName(String name) {
		this.name = name;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Customer setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getSsn() {
		return ssn;
	}

	public Customer setSsn(String ssn) {
		this.ssn = ssn;
		return this;
	}

	public Customer(){

	}

	public ArrayList<Insurance> readInsurances() {
		InsuranceDaoImpl insuranceDao = new InsuranceDaoImpl();
		return insuranceDao.readAll();
	}

	public Insurance readInsurance(int insuranceId) {
		InsuranceDaoImpl insuranceDao = new InsuranceDaoImpl();
		return insuranceDao.read(insuranceId);
	}

	public int inquireHealthPremium(String ssn, int riskCount, Insurance insurance){
		int premium = 0;
		int targerAge = CriterionSetUtil.setTargetAge((TargetInfoCalculator.targetAgeCalculator(ssn)));
		boolean targetSex = TargetInfoCalculator.targetSexCalculator(ssn);
		boolean riskCriterion = CriterionSetUtil.setRiskCriterion(riskCount);

		ArrayList<InsuranceDetail> insuranceDetails = insurance.getInsuranceDetailList();
		for (InsuranceDetail insuranceDetail : insuranceDetails) {
			HealthDetail healthDetail = (HealthDetail) insuranceDetail;
			if (healthDetail.getTargetAge() == targerAge && healthDetail.isTargetSex() == targetSex && (healthDetail.isRiskCriterion()) == riskCriterion) {
				premium = healthDetail.getPremium();
				break;
			}
		}
		if (premium == 0)
			throw new NoResultantException();
		return premium;
	}

	public int inquireFirePremium(BuildingType buildingType, Long collateralAmount, Insurance insurance){
		int premium = 0;
		Long collateralAmountCriterion = CriterionSetUtil.setCollateralAmountCriterion(collateralAmount);

		ArrayList<InsuranceDetail> insuranceDetails = insurance.getInsuranceDetailList();
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

	public int inquireCarPremium(String ssn, Long value, Insurance insurance){
		int premium = 0;
		int targetAge = CriterionSetUtil.setTargetAge(TargetInfoCalculator.targetAgeCalculator(ssn));
		Long valueCriterion = CriterionSetUtil.setValueCriterion(value);

		ArrayList<InsuranceDetail> insuranceDetails = insurance.getInsuranceDetailList();
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

	public Customer registerCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		customer.setName(customerDto.getName())
				.setSsn(customerDto.getSsn())
				.setAddress(customerDto.getAddress())
				.setPhone(customerDto.getPhone())
				.setEmail(customerDto.getEmail())
				.setJob(customerDto.getJob());
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		customerDao.create(customer);
		return customer;
	}

	public User registerUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId())
			.setPassword(userDto.getPassword())
				.setRoleId(userDto.getRoleId());
		UserDaoImpl userDao = new UserDaoImpl();
		userDao.create(user);
		return user;
	}

	public Contract registerContract(Customer customer, ContractDto contractDto, Insurance insurance) {
		Contract contract = null;
		switch (insurance.getInsuranceType()){
			case HEALTH -> {
				HealthContract healthContract = new HealthContract();

				healthContract.setHeight(((HealthContractDto) contractDto).getHeight())
						.setWeight(((HealthContractDto) contractDto).getWeight())
						.setDrinking(((HealthContractDto) contractDto).isDrinking())
						.setSmoking(((HealthContractDto) contractDto).isSmoking())
						.setDriving(((HealthContractDto) contractDto).isDriving())
						.setDangerActivity(((HealthContractDto) contractDto).isDangerActivity())
						.setHavingDisease(((HealthContractDto) contractDto).isHavingDisease())
						.setTakingDrug(((HealthContractDto) contractDto).isTakingDrug())
						.setDiseaseDetail(((HealthContractDto) contractDto).getDiseaseDetail())
						.setInsuranceId(insurance.getId())
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

				ContractDaoImpl contractDao = new ContractDaoImpl();
				contractDao.create(healthContract);
				contract = healthContract;
			}
			case FIRE -> {
				FireContract fireContract = new FireContract();

				fireContract.setBuildingArea(((FireContractDto) contractDto).getBuildingArea())
						.setBuildingType(((FireContractDto) contractDto).getBuildingType())
						.setCollateralAmount(((FireContractDto) contractDto).getCollateralAmount())
						.setActualResidence(((FireContractDto) contractDto).isActualResidence())
						.setSelfOwned(((FireContractDto) contractDto).isSelfOwned())
						.setInsuranceId(insurance.getId())
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

				ContractDaoImpl contractDao = new ContractDaoImpl();
				contractDao.create(fireContract);
				contract = fireContract;
			}
			case CAR -> {
				CarContract carContract = new CarContract();

				carContract.setCarNo(((CarContractDto) contractDto).getCarNo())
						.setCarType(((CarContractDto) contractDto).getCarType())
						.setModelName(((CarContractDto) contractDto).getModelName())
						.setModelYear(((CarContractDto) contractDto).getModelYear())
						.setValue(((CarContractDto) contractDto).getValue())
						.setInsuranceId(insurance.getId())
						.setCustomerId(customer.getId())
						.setPremium(contractDto.getPremium())
						.setConditionOfUw(ConditionOfUw.WAIT);

				ContractDaoImpl contractDao = new ContractDaoImpl();
				contractDao.create(carContract);
				contract = carContract;
			}
		}
		return contract;
	}

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
		this.setPaymentList((ArrayList<Payment>) payments);
	}

	private Payment createPayment(PaymentDto paymentDto) {
		PayType payType = paymentDto.getPayType();
		Payment payment;
		if (payType.equals(PayType.CARD)) {
			payment = new Card();
			((Card)payment).setCardNo(paymentDto.getCardNo())
					.setCvcNo(paymentDto.getCvcNo())
					.setCardType(paymentDto.getCardType())
					.setExpiryDate(paymentDto.getExpiryDate());

		} else {
			payment = new Account();
			((Account)payment).setAccountNo(paymentDto.getAccountNo())
					.setBankType(paymentDto.getBankType());
		}
		payment.setPaytype(payType)
				.setCustomerId(paymentDto.getCustomerId());
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
		if (accidentType == AccidentType.INJURYACCIDENT) {
			accident = new InjuryAccident();
			((InjuryAccident) accident).setInjurySite(accidentReportDto.getInjurySite());
		} else if(accidentType == AccidentType.CARACCIDENT) {
			accident = new CarAccident();
			((CarAccident)accident).setCarNo(accidentReportDto.getCarNo())
					.setPlaceAddress(accidentReportDto.getPlaceAddress())
					.setOpposingDriverPhone(accidentReportDto.getOpposingDriverPhone())
					.setRequestOnSite(accidentReportDto.isRequestOnSite());
		} else if (accidentType == AccidentType.CARBREAKDOWN) {
			accident = new CarBreakdown();
			((CarBreakdown) accident).setCarNo(accidentReportDto.getCarNo())
					.setPlaceAddress(accidentReportDto.getPlaceAddress())
					.setSymptom(accidentReportDto.getSymptom());
		} else {
			accident = new FireAccident();
			((FireAccident)accident).setPlaceAddress(accidentReportDto.getPlaceAddress());
		}
		 accident.setAccidentType(accidentType)
				.setDateOfAccident(accidentReportDto.getDateOfAccident())
				.setDateOfReport(accidentReportDto.getDateOfReport())
				.setCustomerId(this.id);

		AccidentDao accidentDao = new AccidentDaoImpl();
		accidentDao.create(accident);
		return accident;
	}

	public void signUp() {
	}

	public void terminate(){
	}

	@Override
	public String toString() {
		return "고객정보 {" +
				"고객 ID: " + id +
				", 이름: '" + name + '\'' +
				", 주소: '" + address + '\'' +
				", 이메일: '" + email + '\'' +
				", 전화번호: '" + phone + '\'' +
				", 직업: '" + job + '\'' +
				", 주민등록번호: '" + ssn + '\'' +
				", 결제수단: " + paymentList +
				'}';
	}
}