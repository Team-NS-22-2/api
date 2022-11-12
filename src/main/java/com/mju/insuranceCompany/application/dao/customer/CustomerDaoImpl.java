package com.mju.insuranceCompany.application.dao.customer;

import com.mju.insuranceCompany.application.domain.customer.Customer;
import com.mju.insuranceCompany.application.global.exception.MyIllegalArgumentException;
import com.mju.insuranceCompany.application.global.utility.ConsoleColors;
import com.mju.insuranceCompany.application.dao.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * packageName :  domain.customer
 * fileName : JDBCCustomerListImpl
 * author :  규현
 * date : 2022-05-24
 * description :
 * ===========================================================
 * DATE                 AUTHOR                NOTE
 * -----------------------------------------------------------
 * 2022-05-24                규현             최초 생성
 */
public class CustomerDaoImpl extends Dao implements CustomerDao {

    public CustomerDaoImpl() {
        super.connect();
    }

    @Override
    public void create(Customer customer) {

            String query = "INSERT INTO customer (name, job, email, phone, ssn, address) VALUES ('%s', '%s','%s','%s','%s', '%s')";
            String formattedQuery = String.format(query, customer.getName(), customer.getJob(), customer.getEmail(), customer.getPhone(), customer.getSsn(), customer.getAddress());
            int id = super.create(formattedQuery);
            customer.setId(id);
    }

    @Override
    public Customer read(int id) {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE customer_id = "+id;
        try {
        ResultSet rs = super.read(query);
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setJob(rs.getString("job"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setSsn(rs.getString("ssn"));
                customer.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (customer == null) {

            throw new MyIllegalArgumentException(ConsoleColors.RED_BOLD+"ERROR:: ID["+ id + "]에 해당하는 고객 정보가 존재하지 않습니다."+ ConsoleColors.RESET);
        }
        return customer;
    }


    @Override
    public boolean update(int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }


}
