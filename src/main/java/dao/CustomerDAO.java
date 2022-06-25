package dao;

import beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    Customer getLoginCustomer(String email, String password) throws SQLException, InterruptedException;
    boolean isCustomerExist(String email, String password) throws SQLException, InterruptedException;
    void addCustomer(Customer customer) throws SQLException, InterruptedException;
    void updateCustomer( int customerId, Customer customer) throws SQLException, InterruptedException;
    void deleteCustomer( int customerId) throws SQLException, InterruptedException;
    List<Customer> getAllCustomers() throws SQLException, InterruptedException;
    Customer getCustomer(int customerId) throws SQLException, InterruptedException;
    boolean isCustomerEmailExist(String email) throws SQLException, InterruptedException;
}
