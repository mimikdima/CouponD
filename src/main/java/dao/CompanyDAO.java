package dao;

import beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {

    boolean isCompanyExist(String email, String password) throws SQLException, InterruptedException;
    Company getLoginCompany(String email, String password) throws SQLException, InterruptedException;
    void addCompany(Company company) throws SQLException, InterruptedException;
    void updateCompany( int companyId, Company company) throws SQLException, InterruptedException;
    void deleteCompany( int companyId) throws SQLException, InterruptedException;
    List<Company> getAllCompanies() throws SQLException, InterruptedException;
    Company getCompany(int companyId) throws SQLException, InterruptedException;
    boolean isCompanyExistEmail(String email) throws SQLException, InterruptedException;
    boolean isCompanyExistName(String name) throws SQLException, InterruptedException;
}
