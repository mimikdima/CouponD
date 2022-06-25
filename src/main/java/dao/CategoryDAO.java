package dao;

import beans.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {

    void insert(String name) throws SQLException, InterruptedException;
}
