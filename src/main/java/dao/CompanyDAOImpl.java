package dao;

import beans.Company;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.*;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String QUERY_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`companies` WHERE email=? AND password=?) AS res;";
    private static final String QUERY_ADD = "INSERT INTO `coupon-bhp-386`.`companies` (`name`, `email`, `password`) VALUES (?,?,?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-bhp-386`.`companies` SET `name`= ?, `email` = ?, `password` = ? WHERE (`id`) = ?;";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-bhp-386`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-bhp-386`.`companies`;";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-bhp-386`.`companies` WHERE id = ?;";
    private static final String QUERY_EMAIL_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`companies` WHERE email=?) AS res;";
    private static final String QUERY_NAME_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`companies` WHERE name=?) AS res;";
    private static final String QUERY_GET_LOGIN_ID = "SELECT id FROM `coupon-bhp-386`.`companies` WHERE email = ? AND password = ?;";

    @Override
    public boolean isCompanyExist(String email, String password) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EXIST,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public Company getLoginCompany(String email, String password) throws SQLException, InterruptedException {
        Company company = null;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_LOGIN_ID, params);
        try {
            Object first = rows.get(0);
            company = ResultsUtils.companyFromRow((HashMap<String, Object>) first);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return company;
    }

    @Override
    public void addCompany(Company company) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        JDBCUtils.executeQuery(QUERY_ADD, params);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, companyId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        List<Company> companies = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for(Object obj: rows) {
            companies.add(ResultsUtils.companyFromRow((HashMap<String, Object>) obj));
        }
        return companies;
    }

    @Override
    public Company getCompany(int companyId) throws SQLException, InterruptedException {
        Company company = null;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        try {
            Object first = rows.get(0);
            company = ResultsUtils.companyFromRow((HashMap<String, Object>) first);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return company;
    }

    @Override
    public boolean isCompanyExistEmail(String email) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EMAIL_EXIST,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public boolean isCompanyExistName(String name) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, name);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_NAME_EXIST,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }


}
