package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// ADD TRY CATCH WHERE NECESSARY

public class Main {

    private static final Scanner scan = new Scanner(System.in);
    private static final File infile = new File("in.txt");


    public static void main(String[] args) throws FileNotFoundException {

        // *GOAL*
        // read file of accounts with their name, payment day and amount due
        // terminates with "0"
        // EXAMPLE IN.TXT
        //        crunchyroll 1 9.02
        //        chegg 28 14.95
        //        car 20 275.74
        //        spotify 19 5.26
        //        phone 19 84.04
        //        0



        // could be a global private static but would need a try-catch outside of main!
        // scanner for file
        Scanner sc = new Scanner(infile);

        //Make an arrayList of the accounts
        List<Account> accounts = new ArrayList<>();

        accounts = readFile(sc, accounts);

        billCalculator(accounts);

        sc.close();


    }

    public static void billCalculator(List<Account> accounts){

        // ask for dates and separate the month and days from both inputs
        // *possible move to a method outside of main
        System.out.println("Choose a start date (mmdd)");
        int start, startDay, startMonth;


        while(true) {
            start = scan.nextInt();
            if(start < 1000){
                System.out.println("Error: Invalid Entry");
                System.out.println("Must be (mmdd) format");
                System.out.println("Rerun App!");

            }
            startDay = start % 100;
            startMonth = (start - startDay)/100;

            if ((start % 100) > 31 || startMonth > 12) {
                System.out.println("Invalid date: try again");
            }
            else
                break;
        }

        System.out.println("Choose an end date (mmdd)");
        int end, endDay, endMonth;

        while(true) {
            end = scan.nextInt();
            if(end < 1000){
                System.out.println("Error: Invalid Entry");
                System.out.println("Must be (mmdd) format");
                System.out.println("Rerun App!");
            }
            endDay = end % 100;
            endMonth = (end - endDay)/100;

            if ((end % 100) > 31 || endMonth > 12) {
                System.out.println("Invalid date: try again");
            }
            else
                break;
        }

        // choose algorithm to calculate total money spent on bills between
        // the two dates.

        double totalCost = checkBill(startMonth, startDay, endMonth, endDay, accounts);

        // Two methods to get names for Month
        String nameStart = monthName(startMonth);
        String nameEnd = monthName(endMonth);

        // use printf to format decimals
        System.out.printf("From "+ nameStart +" "+startDay+" to "+nameEnd+" "+endDay
                +" , you will pay $%.2f", totalCost);

    }

    public static double checkBill(int sM, int sD, int eM, int eD, List<Account> accounts){

        int multiplier;
        double sumOfAllAccounts = 0;
        double total = 0;

        // only calculates bill within a 1-year period!!

        if((eM < sM) || (eM == sM && eD <= sD)){
            eM = eM + 12;
        }


        multiplier = eM - sM - 1;

        System.out.println("You will pay this much for each of your accounts: ");
        for(Account x: accounts)
        {
            double tempTotal = 0;
            double currentAccountTotal;
            if(x.getDate() >= sD) {
                total = total + x.getCost();
                tempTotal = tempTotal + x.getCost();
            }

            if(x.getDate() <= eD) {
                total = total + x.getCost();
                tempTotal = tempTotal + x.getCost();

            }

            currentAccountTotal =  tempTotal + (x.getCost() * multiplier);

            // printf to format decimals (%.2f)
            System.out.printf("$%.2f for "+ x.getName()+"\n", currentAccountTotal);

            sumOfAllAccounts = sumOfAllAccounts + x.getCost();

        }

        total = total + (sumOfAllAccounts * multiplier);

        return total;

    }

    public static String monthName(int month){

        // return month in string format
         return new DateFormatSymbols().getMonths()[month-1];

    }

    public static List readFile(Scanner sc, List<Account> accounts){

        Account account;

        String n = sc.next();

        while(!n.equals("0"))
        {
            account = new Account(n, sc.nextInt(), sc.nextDouble());
            accounts.add(account);
            // keep reading first string of line until "0"
            n = sc.next();

        }

        System.out.println("You have the following accounts: \n");
        for(Account x : accounts)
        {
            System.out.println("Name: "+x.getName()+" Cost: "+x.getCost()+" Date:" + x.getDate());
        }
        System.out.println("=======================================");


        return accounts;

    }


}
