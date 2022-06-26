package login;

import beans.ClientType;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {

    private static final LoginManager instance = new LoginManager();
    public static LoginManager getInstance(){ return instance;}

    public LoginManager(){
        super();
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        if(clientType == ClientType.ADMINISTRATOR) {
            AdminFacade adminFacade = new AdminFacade();
            if(adminFacade.login(email, password)) {
                return adminFacade;
            }
            return null;
        }
        if(clientType == ClientType.COMPANY) {
            CompanyFacade companyFacade = new CompanyFacade();
            if(companyFacade.login(email, password)) {
                return companyFacade;
            }
            return null;
        }
        if(clientType == ClientType.CUSTOMER) {
            CustomerFacade customerFacade = new CustomerFacade();
            if(customerFacade.login(email, password)) {
                return customerFacade;
            }
            return null;
        }
        return null;
    }
}
