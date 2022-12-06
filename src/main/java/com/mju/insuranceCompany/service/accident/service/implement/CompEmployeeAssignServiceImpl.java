package com.mju.insuranceCompany.service.accident.service.implement;

import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.accident.service.interfaces.CompEmployeeAssignService;
import com.mju.insuranceCompany.service.employee.domain.Department;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.exception.EmployeeIdNotFoundException;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompEmployeeAssignServiceImpl implements CompEmployeeAssignService {

    private final EmployeeRepository employeeRepository;
    private final AccidentRepository accidentRepository;

    /** 배정된 보상담당자를 리턴하는 메소드 */
    @Override
    public Employee assignCompEmployee() {
        List<Employee> compEmployees = employeeRepository.findEmployeeByDepartmentEquals(Department.COMP);
        return assignEmployee(compEmployees);
    }

    /** 보상담당자를 변경하여 다시 배정된 보상담당자를 리턴하는 메소드 */
    @Override
    public Employee changeCompEmployee(int currentEmployeeId) {
        Employee employee = employeeRepository.findById(currentEmployeeId)
                .orElseThrow(EmployeeIdNotFoundException::new);
        List<Employee> compEmployees = employeeRepository.findEmployeeByDepartmentEquals(Department.COMP);
        compEmployees.remove(employee);
        return assignEmployee(compEmployees);
    }

    /** 보상담당자 배정 */
    private Employee assignEmployee(List<Employee> compEmployees) {
        List<Accident> accidents;
        int min = Integer.MAX_VALUE;
        int minId = 0;
        for(Employee employee : compEmployees) {
            accidents = accidentRepository.findAccidentByEmployeeId(employee.getId());
            if(min > accidents.size()) {
                min = accidents.size();
                minId = employee.getId();
            }
        }
        minId = (minId == 0) ? compEmployees.get(0).getId() : minId;
        return employeeRepository.findById(minId).orElseThrow();
    }
}
