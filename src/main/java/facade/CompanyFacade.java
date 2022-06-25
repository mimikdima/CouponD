package facade;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exceptions.CouponSystemException;
import exceptions.ErrMsg;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade{
    private int companyId;

    public CompanyFacade(){
        super();
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        boolean isCompanyExist = this.companyDAO.isCompanyExist(email, password);
        if(isCompanyExist) {
            this.getLoginCompany(email, password);
        }
        return isCompanyExist;
    }

    public void getLoginCompany(String email, String password) throws SQLException, InterruptedException {
        Company currentCompany = this.companyDAO.getLoginCompany(email, password);
        this.companyId = currentCompany.getId();
    }

    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException, CouponSystemException {
        if(this.couponDAO.existNameCouponCompany(companyId, coupon.getTitle())){
            throw new CouponSystemException(ErrMsg.COUPON_TITLE_COMPANY_EXIST);
        }

        this.couponDAO.addCoupon(coupon);
    }

    public void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException, SQLException, InterruptedException {
        if(couponId != coupon.getId()) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_UPDATABLE);
        }

        if(companyId != coupon.getCompanyId()) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_UPDATABLE);
        }

        this.couponDAO.updateCoupon(couponId, coupon);
    }

    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {
        this.couponDAO.deleteCoupon(couponId);
    }

    public List<Coupon> getCurrentCompanyCoupons() throws SQLException, InterruptedException {
        return this.couponDAO.getCouponsByCompany(this.companyId);
    }

    public List<Coupon> getCompanyCouponsByCategory(Category categoryId) throws SQLException, InterruptedException {
        return this.couponDAO.getCouponsCompanyByCategory(this.companyId, categoryId);
    }

    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws SQLException, InterruptedException {
        return this.couponDAO.getCompanyCouponsByMaxPrice(this.companyId, maxPrice);
    }

    public Company getCompanyDetails() throws SQLException, InterruptedException {
        return this.companyDAO.getCompany(this.companyId);
    }
}
