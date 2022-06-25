package dao;

import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAOImpl implements CustomerDAO {

    private static final String QUERY_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`customers` WHERE email=? AND password=?) AS res;";
    private static final String QUERY_ADD = "INSERT INTO `coupon-bhp-386`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?,?,?,?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-bhp-386`.`customers` SET `first_name`= ?, `last_name`= ?, `email` = ?, `password` = ? WHERE (`id`) = ?;";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-bhp-386`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-bhp-386`.`customers`;";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-bhp-386`.`customers` WHERE id = ?;";
    private static final String QUERY_EMAIL_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`customers` WHERE email=?) AS res;";
    private static final String QUERY_GET_LOGIN_CUSTOMER = "SELECT * FROM `coupon-bhp-386`.`customers` WHERE email = ? AND password = ?;";

    @Override
    public Customer getLoginCustomer(String email, String password) throws SQLException, InterruptedException {
        Customer customer = null;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_LOGIN_CUSTOMER, params);
        try {
            Object first = rows.get(0);
            customer = ResultsUtils.customerFromRow((HashMap<String, Object>) first);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return customer;
    }

    @Override
    public boolean isCustomerExist(String email, String password) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EXIST,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        JDBCUtils.executeQuery(QUERY_ADD, params);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customerId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {
        List<Customer> customers = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for(Object obj: rows) {
            customers.add(ResultsUtils.customerFromRow((HashMap<String, Object>) obj));
        }
        return customers;
    }

    @Override
    public Customer getCustomer(int customerId) throws SQLException, InterruptedException {
        Customer customer = null;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        try {
            Object first = rows.get(0);
            customer = ResultsUtils.customerFromRow((HashMap<String, Object>) first);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return customer;
    }

    @Override
    public boolean isCustomerEmailExist(String email) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EMAIL_EXIST,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }
}
