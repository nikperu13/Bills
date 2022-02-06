package com.company;

public class Account {
    private String type;
    private String name;
    private int date;
    private double cost;
    private double balance = -1;
    private double newCost;
    private int month = 0;

    //billType/billName/billDate/billAmount/balanceAmount/newBillAmount

    public Account(String type, String name, int date, double cost) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.cost = cost;
    }
    public Account(String type, String name, int date, double cost, int month) {
        this.type= type;
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.month = month;
    }

    public Account(String type, String name, int date, double cost, double balance) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.balance = balance;
    }
    public Account(String type, String name, int date, double cost, double balance, int month) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.balance = balance;
        this.month = month;
    }


    public Account(String type, String name, int date, double cost, double balance, double newCost) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.balance = balance;
        this.newCost = newCost;
    }
    public Account(String type, String name, int date, double cost, double balance, double newCost, int month) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.cost = cost;
        this.balance = balance;
        this.newCost = newCost;
        this.month = month;
    }

    public Account(){
        name = "";
        date = 0;
        cost = 0;
        type = "";
        balance = 0;
        newCost = 0;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getNewCost() {
        return newCost;
    }

    public void setNewCost(double newCost) {
        this.newCost = newCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month= month;
    }

    @Override
    public String toString() {
        return  "Name = " + name + " " +
                ", Date = " + date +
                ", Cost = " + cost
                ;
    }
}
