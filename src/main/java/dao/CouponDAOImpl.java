package dao;

import beans.Category;
import beans.Coupon;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDAOImpl implements CouponDAO{

    private static final String QUERY_ADD = "INSERT INTO `coupon-bhp-386`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`) VALUES (?,?,?,?,?,?,?,?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-bhp-386`.`coupons` SET `company_id`= ?, `category_id` = ?, `title` = ?,`description`= ?, `start_date` = ?, `end_date` = ?,`amount`= ?, `price` = ? WHERE (`id`) = ?;";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-bhp-386`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-bhp-386`.`coupons`;";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-bhp-386`.`coupons` WHERE id = ?;";

    private static final String QUERY_EXIST_COUPON_NAME_COMPANY = "SELECT EXISTS(SELECT * FROM `coupon-bhp-386`.`coupons` WHERE company_id=? AND title=?) AS res;";
    private static final String QUERY_GET_COUPONS_BY_COMPANY = "SELECT * FROM `coupon-bhp-386`.`coupons` WHERE company_id = ?;";
    private static final String QUERY_GET_COUPONS_COMPANY_BY_CATEGORY = "SELECT * FROM `coupon-bhp-386`.`coupons` WHERE company_id = ? AND category_id = ?;";
    private static final String QUERY_GET_COUPONS_COMPANY_BY_MAX_PRICE = "SELECT * FROM `coupon-bhp-386`.`coupons` WHERE company_id = ? AND price < ?;";

    private static final String QUERY_GET_COUPONS_BY_CUSTOMER = "SELECT c.* FROM `coupon-bhp-386`.`coupons` c " +
                                                                "LEFT JOIN `coupon-bhp-386`.`customers_vs_coupons` cvc ON c.id = cvc.coupon_id " +
                                                                "WHERE cvc.customer_id = ?;";

    private static final String QUERY_GET_COUPONS_CUSTOMER_BY_CATEGORY = "SELECT c.* FROM `coupon-bhp-386`.`coupons` c " +
                                                                         "LEFT JOIN `coupon-bhp-386`.`customers_vs_coupons` cvc ON c.id = cvc.coupon_id " +
                                                                         "WHERE cvc.customer_id = ? AND c.category_id = ?;";

    private static final String QUERY_GET_COUPONS_CUSTOMER_BY_MAX_PRICE = "SELECT c.* FROM `coupon-bhp-386`.`coupons` c " +
                                                                           "LEFT JOIN `coupon-bhp-386`.`customers_vs_coupons` cvc ON c.id = cvc.coupon_id " +
                                                                           "WHERE cvc.customer_id = ? AND c.price < ?;";

    private static final String QUERY_PURCHASE_COUPON = "INSERT INTO `coupon-bhp-386`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?)";


    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, Category.getCategoryIntValue(coupon.getCategoryId()));
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        JDBCUtils.executeQuery(QUERY_ADD, params);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, Category.getCategoryIntValue(coupon.getCategoryId()));
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, couponId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public Coupon getCoupon(int couponId) throws SQLException, InterruptedException {
        Coupon coupon = null;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        try {
            Object first = rows.get(0);
            coupon = ResultsUtils.couponFromRow((HashMap<String, Object>) first);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return coupon;
    }

    @Override
    public boolean existNameCouponCompany(int companyId, String title) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, title);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EXIST_COUPON_NAME_COMPANY,params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }


    @Override
    public List<Coupon> getCouponsByCompany(int companyId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_BY_COMPANY, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCouponsCompanyByCategory(int companyId, Category categoryId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, Category.getCategoryIntValue(categoryId));
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_COMPANY_BY_CATEGORY, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_COMPANY_BY_MAX_PRICE, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_BY_CUSTOMER, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByCategory(int customerId, Category categoryId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, Category.getCategoryIntValue(categoryId));
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_CUSTOMER_BY_CATEGORY, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, maxPrice);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_COUPONS_CUSTOMER_BY_MAX_PRICE, params);
        for(Object obj: rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public void purchaseCoupon(int customerId, int couponId) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        JDBCUtils.executeQuery(QUERY_PURCHASE_COUPON, params);
    }


}
