package db;

import beans.Category;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;

import java.sql.SQLException;

/**
 * Created by kobis on 08 May, 2022
 */
public class DatabaseManager {

    private static final DatabaseManager instance = new DatabaseManager();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private static final String QUERY_CREATE_SCHEMA = "CREATE SCHEMA `coupon-bhp-386`;";
    private static final String QUERY_DROP_SCHEMA = "DROP DATABASE `coupon-bhp-386`;";
    private static final String QUERY_CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon-bhp-386`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email`VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String QUERY_CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon-bhp-386`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name`VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String QUERY_CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon-bhp-386`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";

    private static final String QUERY_CREATE_TABLE_COUPONS = "CREATE TABLE `coupon-bhp-386`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` INT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `coupon-bhp-386`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `coupon-bhp-386`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    private static final String QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `coupon-bhp-386`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `coupon-bhp-386`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `coupon-bhp-386`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    public void dropCreateStrategy() {

        try {
            JDBCUtils.executeQuery(QUERY_DROP_SCHEMA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            JDBCUtils.executeQuery(QUERY_CREATE_SCHEMA);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COMPANIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMERS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CATEGORIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COUPONS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS);
            insertCategories();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertCategories() throws SQLException, InterruptedException {
        for(Category c: Category.values()) {
            String name = c.name();
            categoryDAO.insert(name);
        }
    }

}
