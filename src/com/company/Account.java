package com.company;

public class Account {
    private String name;
    private int date;
    private double cost;

    public Account(String name, int date, double cost) {
        this.name = name;
        this.date = date;
        this.cost = cost;
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

    public Account(){
        name = "";
        date = 0;
        cost = 0;
    }

    @Override
    public String toString() {
        return  "Name = " + name + " " +
                ", Date = " + date +
                ", Cost = " + cost
                ;
    }
}
