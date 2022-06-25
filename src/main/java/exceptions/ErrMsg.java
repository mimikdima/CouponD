package exceptions;

public enum ErrMsg {

    WRONG_EMAIL_OR_PASSWORD("email or password wrong..."),
    COMPANY_EMAIL_EXIST("Company email exist."),
    COMPANY_NAME_EXIST("Company name exist."),
    COMPANY_ID_CANNOT_CHANGE("Cannot update company ID."),
    COMPANY_NAME_CANNOT_CHANGE("Cannot update company NAME."),
    CUSTOMER_EMAIL_EXIST("Customer email already exist"),
    CUSTOMER_ID_CANNOT_CHANGE("Cannot update customer ID"),
    COUPON_ID_NOT_UPDATABLE("coupon ID not updatable"),
    COMPANY_ID_NOT_UPDATABLE("company ID not updatable"),
    COUPON_TITLE_COMPANY_EXIST("coupon for this company with this title already exist"),
    NO_COUPON_LEFT("No coupon left"),
    EXPIRED_COUPON("Expired coupon");

    private String msg;
    ErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
