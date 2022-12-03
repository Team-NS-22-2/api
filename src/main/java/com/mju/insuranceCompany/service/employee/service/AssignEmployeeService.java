package com.mju.insuranceCompany.service.employee.service;

import com.mju.insuranceCompany.service.accident.domain.Accident;
import com.mju.insuranceCompany.service.accident.repository.AccidentRepository;
import com.mju.insuranceCompany.service.employee.domain.Department;
import com.mju.insuranceCompany.service.employee.domain.Employee;
import com.mju.insuranceCompany.service.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AccidentRepository accidentRepository;

    /**
     * 보상담당자 직원을 배정하는 메소드.
     * @return 배정된 보상담당자 직원
     */
    public Employee assignCompEmployee() {
        List<Employee> compEmployees = employeeRepository.findEmployeeByDepartmentEquals(Department.COMP);
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
