package toodles.com.za.work.model;


import java.util.Date;

/**
 * Created by smoit on 2017/04/05.
 */

public class User {


    //(email,password,cell,name,surname,gender,dateofbirth,signupdate)
    private long custID;
    private String role;
    private String name;
    private String email;
    private String cell;
    private String password;
    private String surname;
    private String gender;
    private String dateofbirth;
    private String loginType;

    public static final String CUST_ID_COL = "CUSTID";
    public static final String EMAIL_COL = "EMAIL";
    public static final String PASSWORD_COL = "PASSWORD";
    public static final String CELL_COL = "CELL";
    public static final String NAME_COL = "NAME";
    public static final String SURNAME_COL = "SURNAME";
    public static final String GENDER_COL = "GENDER";
    public static final String DATEOFBIRTH_COL = "DATEOFBIRTH";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String LOGIN_TYPE_COL = "LOGIN_TYPE";


    public static final String LOGIN_TYPE_FACEBOOK = "FACEBOOK_LOGIN";
    public static final String LOGIN_TYPE_APP = "APP_LOGIN";
    public static final String LOGIN_TYPE_GOOGLES = "GOOGLE_LOGIN";

    public User() {
    }

    public User(long cust_id,String name, String email,String cell, String password, String surname, String gender, String dateofbirth,String loginType) {

        this.custID = cust_id;
        this.name = name;
        this.email = email;
        this.cell = cell;
        this.password = password;
        this.surname = surname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.loginType = loginType;
    }


    public long getCustID() {
        return custID;
    }

    public void setCustID(long custID) {
        this.custID = custID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "User{" +
                "custID=" + custID +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cell='" + cell + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", dateofbirth='" + dateofbirth + '\'' +
                ", LoginType='" + loginType + '\'' +
                '}';
    }
}
