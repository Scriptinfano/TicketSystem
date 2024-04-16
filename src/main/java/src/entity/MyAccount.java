package src.entity;

import java.util.Date;

public class MyAccount {
    private int discountSequence;
    private int id;
    private String accountType;
    private String payWay;
    private Date singIn;
    private String userNickname;
    private String idCard;
    private String phoneNumber;
    private String userName;
    private String gender;
    private int age;

    public MyAccount(int discountSequence, int id, String accountType, String payWay, Date singIn, String userNickname, String idCard, String phoneNumber, String userName, String gender, int age) {
        this.discountSequence = discountSequence;
        this.id = id;
        this.accountType = accountType;
        this.payWay = payWay;
        this.singIn = singIn;
        this.userNickname = userNickname;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.gender = gender;
        this.age = age;
    }

    public int getDiscountSequence() {
        return discountSequence;
    }

    public void setDiscountSequence(int discountSequence) {
        this.discountSequence = discountSequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Date getSingIn() {
        return singIn;
    }

    public void setSingIn(Date singIn) {
        this.singIn = singIn;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
