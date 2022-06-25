package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerFacade extends ClientFacade{

    private int customerId;

    public CustomerFacade(){
        super();
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        boolean isCustomerExist = this.customerDAO.isCustomerExist(email, password);
        if(isCustomerExist) {
            this.getLoginCustomer(email, password);
        }
        return isCustomerExist;
    }

    public void getLoginCustomer(String email, String password) throws SQLException, InterruptedException {
        Customer currentCustomer = this.customerDAO.getLoginCustomer(email, password);
        this.customerId = currentCustomer.getId();
    }
    
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException, SQLException, InterruptedException {
        if(coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPON_LEFT);
        }
        if(coupon.getEndDate().before(new Date())){
            throw new CouponSystemException(ErrMsg.EXPIRED_COUPON);
        }

        coupon.setAmount(coupon.getAmount() - 1);
        couponDAO.updateCoupon(coupon.getId(), coupon);
        couponDAO.purchaseCoupon(coupon.getId(), customerId);
    }

    public List<Coupon> getCustomerCoupons() throws SQLException, InterruptedException {
        return this.couponDAO.getCustomerCoupons(this.customerId);
    }

    public List<Coupon> getCustomerCouponsByCategory(Category categoryId) throws SQLException, InterruptedException  {
        return this.couponDAO.getCustomerCouponsByCategory(this.customerId, categoryId);
    }

    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws SQLException, InterruptedException  {
        return this.couponDAO.getCustomerCouponsByMaxPrice(this.customerId, maxPrice);
    }

    public Customer getCustomerDetails() throws SQLException, InterruptedException {
        return this.customerDAO.getCustomer(this.customerId);
    }

}
