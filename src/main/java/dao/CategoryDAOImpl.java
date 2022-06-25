package dao;

import beans.Category;
import db.JDBCUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String QUERY_INSERT = "INSERT INTO `coupon-bhp-386`.`categories` (`name`) VALUES (?);";

    @Override
    public void insert(String name) throws SQLException, InterruptedException {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1, name);
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }
}
