package dao;

public interface loginDAO {

    boolean isCompanyLoginCorrect(String email, String password);
    boolean isCustomerLoginCorrect(String email, String password);
}
