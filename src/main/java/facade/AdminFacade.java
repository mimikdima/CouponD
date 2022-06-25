package facade;

import beans.Company;
import beans.Customer;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade{

    public AdminFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws SQLException, InterruptedException, CouponSystemException {
        if(this.companyDAO.isCompanyExistEmail(company.getEmail())){
            throw new CouponSystemException(ErrMsg.COMPANY_EMAIL_EXIST);
        }
        if(this.companyDAO.isCompanyExistName(company.getName())){
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_EXIST);
        }
        this.companyDAO.addCompany(company);
    }

    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException, CouponSystemException {
        Company currentCompany = this.companyDAO.getCompany(companyId);
        if(currentCompany.getId() != company.getId()) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_CANNOT_CHANGE);
        }
        if(!currentCompany.getName().equals(company.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_CANNOT_CHANGE);
        }

        this.companyDAO.updateCompany(companyId, company);
    }

    public void deleteCompany(int companyId) throws SQLException, InterruptedException {
        this.companyDAO.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        return this.companyDAO.getAllCompanies();
    }

    public Company getCompany(int companyId) throws SQLException, InterruptedException {
        return this.companyDAO.getCompany(companyId);
    }

    public boolean isCompanyExist(String email, String password) throws SQLException, InterruptedException {
        return this.companyDAO.isCompanyExist(email, password);
    }

    public void addCustomer(Customer customer) throws SQLException, InterruptedException, CouponSystemException {
        if(this.customerDAO.isCustomerEmailExist(customer.getEmail())){
            throw new CouponSystemException(ErrMsg.CUSTOMER_EMAIL_EXIST);
        }
        this.customerDAO.addCustomer(customer);
    }

    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException, CouponSystemException {
        if(customerId != customer.getId()) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_ID_CANNOT_CHANGE);
        }

        this.customerDAO.updateCustomer(customerId, customer);
    }

    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {
        this.customerDAO.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {
        return this.customerDAO.getAllCustomers();
    }

    public Customer getCustomer(int customerId) throws SQLException, InterruptedException {
        return this.customerDAO.getCustomer(customerId);
    }

    public boolean isCustomerExist(String email, String password) throws SQLException, InterruptedException {
        return this.customerDAO.isCustomerExist(email, password);
    }
}
