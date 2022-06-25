package facade;

import dao.*;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompanyDAO companyDAO;
    protected CustomerDAO customerDAO;
    protected CouponDAO couponDAO;

    public ClientFacade () {
        this.companyDAO = new CompanyDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        this.couponDAO = new CouponDAOImpl();
    }

    public abstract boolean login (String email, String password) throws SQLException, InterruptedException;
}
