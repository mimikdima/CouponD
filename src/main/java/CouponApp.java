import beans.*;
import dao.*;
import db.DatabaseManager;
import exceptions.CouponSystemException;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import login.LoginManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CouponApp {

    public static void main(String[] args) throws SQLException, InterruptedException, CouponSystemException {

        System.out.println("START");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Admin email");
        String email = scanner.nextLine();
        System.out.println("Admin password");
        String password = scanner.nextLine();

        DatabaseManager.getInstance().dropCreateStrategy();

        LoginManager loginManager = new LoginManager();
        ClientFacade facade = loginManager.login(email,password, ClientType.ADMINISTRATOR);
        // admin@admin.com    admin
        // company111@mail.ru   2222
        // dima@mail.ru         1111

        if(facade instanceof AdminFacade) {
            System.out.println("------------- START ADMIN FACADE -------------");
            System.out.println("------------- Company DAO ADDING -------------");
            Company company1 = new Company("company 1", "company1@mail.ru", "1111");
            Company company2 = new Company("company 2", "company2@mail.ru", "1111");
            Company company3 = new Company("company 3", "company3@mail.ru", "1111");

            ((AdminFacade) facade).addCompany(company1);
            ((AdminFacade) facade).addCompany(company2);
            ((AdminFacade) facade).addCompany(company3);

            ((AdminFacade) facade).getAllCompanies().forEach(System.out::println);
            System.out.println("END");

            System.out.println("------------- Company DAO UPDATING -------------");
            Company companyUp = new Company(1,"company 1", "company111@mail.ru", "2222");
            ((AdminFacade) facade).updateCompany(1, companyUp);
            ((AdminFacade) facade).getAllCompanies().forEach(System.out::println);
            System.out.println("END");

            System.out.println("------------- Company DAO DELETE -------------");
            ((AdminFacade) facade).deleteCompany(3);
            ((AdminFacade) facade).getAllCompanies().forEach(System.out::println);
            System.out.println("END");

            System.out.println("------------- Company DAO GET ONE -------------");
            System.out.println(((AdminFacade) facade).getCompany(1));
            System.out.println("END");

            System.out.println("------------- Company DAO EXIST COMPANY -------------");
            System.out.println(((AdminFacade) facade).isCompanyExist("company111@mail.ru", "2222"));
            System.out.println("END");



            System.out.println("START");
            System.out.println("------------- Customer DAO ADDING -------------");
            Customer customer1 = new Customer("dima", "ttt", "dima@mail.ru", "1111");
            Customer customer2 = new Customer("pasha", "sss", "pasha@mail.ru", "1111");
            Customer customer3 = new Customer("vika", "zzz", "vika@mail.ru", "1111");
            ((AdminFacade) facade).addCustomer(customer1);
            ((AdminFacade) facade).addCustomer(customer2);
            ((AdminFacade) facade).addCustomer(customer3);
            ((AdminFacade) facade).getAllCustomers().forEach(System.out::println);

            System.out.println("------------- Customer DAO UPDATING -------------");
            Customer customerUpdate = new Customer(1, "TTT", "ttt", "dima@mail.ru", "1111");
            ((AdminFacade) facade).updateCustomer(1, customerUpdate);
            ((AdminFacade) facade).getAllCustomers().forEach(System.out::println);

            System.out.println("------------- Customer DAO DELETE -------------");
            ((AdminFacade) facade).deleteCustomer(3);
            ((AdminFacade) facade).getAllCustomers().forEach(System.out::println);
            System.out.println("END");

            System.out.println("------------- Customer DAO GET ONE -------------");
            System.out.println(((AdminFacade) facade).getCustomer(2));
            System.out.println("END");

            System.out.println("------------- Customer DAO EXIST COMPANY -------------");
            System.out.println(((AdminFacade) facade).isCustomerExist("company1@mail.ru", "1111"));
            System.out.println("END");
        }

        System.out.println("START");
        System.out.println("Company email");
        String emailCompany = scanner.nextLine();
        System.out.println("Company password");
        String passwordCompany = scanner.nextLine();

        ClientFacade facadeCompany = loginManager.login(emailCompany, passwordCompany, ClientType.COMPANY);

        if(facadeCompany instanceof CompanyFacade) {

            System.out.println("------------- CompanyFacade Testing -------------");
            System.out.println("------------- Coupon DAO ADDING -------------");
            Coupon coupon1 = new Coupon(1, Category.getCategoryFromDBValue(1), "coupon1", "coupon1111", Date.valueOf(LocalDate.of(2022, 1 ,15)), Date.valueOf(LocalDate.of(2023, 2 ,15)), 10, 100);
            Coupon coupon2 = new Coupon(1, Category.getCategoryFromDBValue(2), "coupon2", "coupon2222", Date.valueOf(LocalDate.of(2022, 3 ,15)), Date.valueOf(LocalDate.of(2023, 4 ,15)), 20, 200);
            Coupon coupon3 = new Coupon(2, Category.getCategoryFromDBValue(3), "coupon3", "coupon3333", Date.valueOf(LocalDate.of(2022, 5 ,15)), Date.valueOf(LocalDate.of(2023, 6 ,15)), 30, 300);

            ((CompanyFacade) facadeCompany).addCoupon(coupon1);
            ((CompanyFacade) facadeCompany).addCoupon(coupon2);
            ((CompanyFacade) facadeCompany).addCoupon(coupon3);
            ((CompanyFacade) facadeCompany).getCurrentCompanyCoupons().forEach(System.out::println);

            System.out.println("------------- Coupon DAO UPDATING -------------");
            Coupon couponUpdate = new Coupon(1, 1, Category.getCategoryFromDBValue(3), "couponNew", "coupon1111", Date.valueOf(LocalDate.of(2022, 6 ,15)), Date.valueOf(LocalDate.of(2023, 8 ,15)), 10, 100);
            ((CompanyFacade) facadeCompany).updateCoupon(1, couponUpdate);
            ((CompanyFacade) facadeCompany).getCurrentCompanyCoupons().forEach(System.out::println);

            System.out.println("------------- Coupon DAO DELETE -------------");
            ((CompanyFacade) facadeCompany).deleteCoupon(2);
            ((CompanyFacade) facadeCompany).getCurrentCompanyCoupons().forEach(System.out::println);
            System.out.println("END");

            System.out.println("-------------  DAO GET COMPANY Coupons -------------");
            System.out.println(((CompanyFacade) facadeCompany).getCurrentCompanyCoupons());
            System.out.println("END");

            System.out.println("-------------  DAO GET COMPANY Coupons BY CATEGORY -------------");
            System.out.println(((CompanyFacade) facadeCompany).getCompanyCouponsByCategory(Category.PC));
            System.out.println("END");

            System.out.println("-------------  DAO GET COMPANY Coupons BY MAX PRICE -------------");
            System.out.println(((CompanyFacade) facadeCompany).getCompanyCouponsByMaxPrice(300));
            System.out.println("END");

            System.out.println("-------------  DAO GET COMPANY DETAILS -------------");
            System.out.println(((CompanyFacade) facadeCompany).getCompanyDetails());
            System.out.println("END");
        }

        System.out.println("START");
        System.out.println("Customer email");
        String emailCustomer = scanner.nextLine();
        System.out.println("Customer password");
        String passwordCustomer = scanner.nextLine();

        ClientFacade facadeCustomer = loginManager.login(emailCustomer, passwordCustomer, ClientType.CUSTOMER);

        if(facadeCustomer instanceof CustomerFacade) {

            System.out.println("------------- CustomerFacade Testing -------------");
            System.out.println("-------------  PURCHASE CUSTOMER Coupons -------------");
            ((CustomerFacade) facadeCustomer).purchaseCoupon(new Coupon(1, 1, Category.getCategoryFromDBValue(3), "couponNew", "coupon1111", Date.valueOf(LocalDate.of(2022, 6 ,15)), Date.valueOf(LocalDate.of(2023, 8 ,15)), 10, 100));

            System.out.println("-------------  DAO GET CUSTOMER Coupons -------------");
            System.out.println(((CustomerFacade) facadeCustomer).getCustomerCoupons());
            System.out.println("END");

            System.out.println("-------------  DAO GET CUSTOMER Coupons BY CATEGORY -------------");
            System.out.println(((CustomerFacade) facadeCustomer).getCustomerCouponsByCategory(Category.PC));
            System.out.println("END");

            System.out.println("-------------  DAO GET CUSTOMER Coupons BY MAX PRICE -------------");
            System.out.println(((CustomerFacade) facadeCustomer).getCustomerCouponsByMaxPrice(300));
            System.out.println("END");

            System.out.println("-------------  DAO GET CUSTOMER DETAILS -------------");
            System.out.println(((CustomerFacade) facadeCustomer).getCustomerDetails());
            System.out.println("END");
        }

        scanner.close();

    }
}
