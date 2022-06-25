package dao;

import beans.Category;
import beans.Coupon;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {

    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;
    void updateCoupon( int couponId, Coupon coupon) throws SQLException, InterruptedException;
    void deleteCoupon( int couponId) throws SQLException, InterruptedException;
    List<Coupon> getAllCoupons() throws SQLException, InterruptedException;
    Coupon getCoupon(int couponId) throws SQLException, InterruptedException;

    boolean existNameCouponCompany(int companyId, String title) throws SQLException, InterruptedException;
    List<Coupon> getCouponsByCompany(int companyId) throws SQLException, InterruptedException;
    List<Coupon> getCouponsCompanyByCategory(int companyId, Category categoryId) throws SQLException, InterruptedException;
    List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException;

    List<Coupon> getCustomerCoupons(int customerId) throws SQLException, InterruptedException;
    List<Coupon> getCustomerCouponsByCategory(int customerId, Category categoryId) throws SQLException, InterruptedException;
    List<Coupon> getCustomerCouponsByMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException;

    void purchaseCoupon(int customerId, int couponId) throws SQLException, InterruptedException;
}
