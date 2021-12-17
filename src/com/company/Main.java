package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


// ADD TRY CATCH WHERE NECESSARY

public class Main {

    private static final Scanner scan = new Scanner(System.in);
    private static final File infile = new File("in.txt");


    public static void main(String[] args) throws FileNotFoundException {

        // *GOAL*
        // read file of accounts with their name, payment day and amount due
        // terminates with "0"
        // modify in.txt file in order to see your bills!
        // EXAMPLE IN.TXT


        // could be a global private static but would need a try-catch outside of main!
        // scanner for file is "sc"
        Scanner sc = new Scanner(infile);

        List<Account> accounts = new ArrayList<>();

        accounts = readFile(sc, accounts);

        List<LocalDate> listOfDates = rangeOfBills();

        billCalc2(listOfDates, accounts);

        scan.close();
        sc.close();

    }

    public static void billCalc2(List<LocalDate> dates, List<Account> accounts){

        double totalBill = 0.0;


        for (Account account: accounts){
            double accountBill = 0.0;

            for(LocalDate date: dates){
                if(account.getDate() == date.getDayOfMonth()){
                    accountBill += account.getCost();
                    totalBill += account.getCost();
                }
            }
            System.out.printf("Account Name: "+account.getName()+"\t\t Total paid over period: $%.2f\n", accountBill);
        }


        // format date
        LocalDate start = dates.get(0);
        LocalDate end = dates.get(dates.size()-1);
        String formattedStartDate = start.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String formattedEndDate = end.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));


        System.out.printf("\nFrom "+ formattedStartDate+" to "+ formattedEndDate+" , you will pay $%.2f toward your bills!", totalBill);


    }

    public static List<LocalDate> rangeOfBills() {

        boolean notValid = true;
        LocalDate startDate = null;
        LocalDate endDate = null;
        while(notValid) {
            try {
                System.out.println("Start Date (mm/dd/yyyy)");
                String day1 = scan.nextLine();
                System.out.println("End Date (mm/dd/yyyy)");
                String day2 = scan.nextLine();

                // year/month/dayOfMonth
                startDate = LocalDate.of(Integer.parseInt(day1.substring(6,10)),Integer.parseInt(day1.substring(0,2)),Integer.parseInt(day1.substring(3,5)));
                endDate = LocalDate.of(Integer.parseInt(day2.substring(6,10)),Integer.parseInt(day2.substring(0,2)),Integer.parseInt(day2.substring(3,5))+1);

                notValid = false;
            } catch (Exception e) {
                System.out.println("Date is invalid, check Exception, must be in format (mm/dd/yyyy)");
                System.out.println("Ex. 07/02/2020");
                System.out.println("Error: "+ e);

            }
        }

        // returns List<LocalDate> of dates within the range
        return startDate.datesUntil(endDate).collect(Collectors.toList());

    }

    public static List<Account> readFile(Scanner sc, List<Account> accounts){

        Account account;

        String n = sc.next();

        while(!n.equals("0"))
        {
            // nameBill/dateBill/priceBill
            account = new Account(n, sc.nextInt(), sc.nextDouble());
            if(account.getDate() >= 31){
                System.out.println("You're bill date will be changed to the 28th for "+account.getName()+"!");
                account.setDate(28);
            }
            accounts.add(account);
            // keep reading first string of line until "0"
            n = sc.next();

        }

        System.out.println("\nYou have the following accounts: ");
        for(Account x : accounts)
        {
            System.out.println("Name: "+x.getName()+"\t\t Cost: $"+x.getCost()+"\t\t Date: " + x.getDate());
        }
        System.out.println("=======================================");


        return accounts;

    }

}
