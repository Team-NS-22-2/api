package com.mju.insuranceCompany.application.login;

import com.mju.insuranceCompany.application.dao.customer.CustomerDaoImpl;
import com.mju.insuranceCompany.application.dao.employee.EmployeeDaoImpl;
import com.mju.insuranceCompany.application.dao.user.UserDaoImpl;
import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.domain.employee.Employee;
import com.mju.insuranceCompany.application.global.exception.InputException;
import com.mju.insuranceCompany.application.global.exception.LoginIdFailedException;
import com.mju.insuranceCompany.application.global.exception.LoginPwFailedException;
import com.mju.insuranceCompany.application.global.utility.MyBufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class Login {

    public int menuLogin() {
        MyBufferedReader br = new MyBufferedReader(new InputStreamReader(System.in));
        String id = "", password = "";
        int roleId = -1;
        System.out.println("<< 로그인 >>");
        loopId: while(true){
            try {
                id = (String) br.verifyRead("아이디: ", id);
                if(id.equals("0")) break loopId;                 // 로그인 취소
                loopPw: while (true) {
                    password = (String) br.verifyRead("비밀번호: ", password);
                    if(password.equals("0")) break loopId;        // 로그인 취소
                    try {
                        roleId = login(id, password);
                        break loopId;
                    }
                    catch (LoginPwFailedException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (LoginIdFailedException e) {
                        System.out.println(e.getMessage());
                        break loopPw;
                    }
                }
            }
            catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
        return roleId;
    }

    private int login(String id, String password) {
        return new UserDaoImpl().login(id, password);
    }

    public Customer loginCustomer() throws IOException {
        int customerId = this.menuLogin();
        if(customerId < 0) return null;
        return new CustomerDaoImpl().read(customerId);
    }

    public Employee loginEmployee() throws IOException {
        int employeeId = this.menuLogin();
        if(employeeId < 0) return null;
        return new EmployeeDaoImpl().read(employeeId);
    }

}
