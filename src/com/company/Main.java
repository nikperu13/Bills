package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;


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

        billCalc(listOfDates, accounts);

        scan.close();
        sc.close();

    }

    public static void billCalc(List<LocalDate> dates, List<Account> accounts){

        // DETERMINE NEW BALANCE AND IF BALANCE BECOMES 0, DELETE ACCOUNT(BALANCE) OR
        // DETERMINE NEW BILL FOR ACCOUNT ONCE BALANCE HITS 0 (BALANCE AND BILL)


        double totalBill = 0.0;

        for (Account account: accounts){
            double accountBill = 0.0;


            for(LocalDate date: dates){
                if(account.getBalance()>0 || account.getBalance()==-1){
                    // if accountBill has "special date" ie "1/26"
                    if((account.getDate() == date.getDayOfMonth()) && (account.getMonth() == date.getMonthValue())) {

                        accountBill += account.getCost();
                        totalBill += account.getCost();

                        if(account.getType().equalsIgnoreCase("Balance and Bill")){
                            if(account.getBalance()>0){
                                account.setBalance(account.getBalance()-(account.getCost()-account.getNewCost()));
                                if(account.getBalance()==0){
                                    account.setCost(account.getNewCost());
                                    account.setBalance(-1);
                                }
                            }
                        }else if(account.getType().equalsIgnoreCase("Balance")){
                            if(account.getBalance()>0){
                                account.setBalance(account.getBalance()-account.getCost());
                            }
                        }

                    }
                    // if accountBill has normal date ie "15"
                    else if(account.getDate() == date.getDayOfMonth() && account.getMonth() == 0){
                        accountBill += account.getCost();
                        totalBill += account.getCost();

                        if(account.getType().equalsIgnoreCase("Balance and Bill")){
                            if(account.getBalance()>0){
                                account.setBalance(account.getBalance()-(account.getCost()-account.getNewCost()));
                                if(account.getBalance()==0){
                                    account.setCost(account.getNewCost());
                                    account.setBalance(-1);
                                }
                            }
                        }else if(account.getType().equalsIgnoreCase("Balance")){
                            if(account.getBalance()>0){
                                account.setBalance(account.getBalance()-account.getCost());
                            }
                        }

                    }

                }

            }
            if(accountBill!=0){
                System.out.printf("Account Name: "+account.getName()+"\t\t Total paid over period: $%.2f\n", accountBill);

                if(account.getBalance() == 0){
                    System.out.println("You have paid off "+account.getName()+" balance!!!");
                }
                else if((account.getType().equalsIgnoreCase("Balance and Bill")) && account.getBalance()==-1){
                    System.out.println("You have paid off balance from "+account.getName()+" bill!!");
                }

                if(account.getBalance()>0){
                    System.out.printf("You have $%.2f left to pay of balance for "+account.getName()+"!!\n", account.getBalance());
                }
            }

        }


        // format LocalDate into desired string
        LocalDate start = dates.get(0);
        LocalDate end = dates.get(dates.size()-1);
        String formattedStartDate = start.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String formattedEndDate = end.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));


        System.out.printf("\nFrom "+ formattedStartDate+" to "+ formattedEndDate+", you will pay $%.2f toward your bills!", totalBill);


    }

    public static List<LocalDate> rangeOfBills() {

        boolean notValid = true;
        LocalDate startDate;
        LocalDate endDate;
        List<LocalDate> dates = new ArrayList<>();
        while(notValid) {
            try {
                System.out.println("Start Date (mm/dd/yyyy)");
                String day1 = scan.nextLine();
                if(day1.equalsIgnoreCase("today")){
                    // today's date in mm/dd/yyyy
                    // format Date into string
                    Format f = new SimpleDateFormat("MM/dd/yyyy");
                    day1 = f.format(new Date());
                    System.out.println(day1);
                }
                System.out.println("End Date (mm/dd/yyyy)");
                String day2 = scan.nextLine();

                // year/month/dayOfMonth
                startDate = LocalDate.of(Integer.parseInt(day1.substring(6,10)),Integer.parseInt(day1.substring(0,2)),Integer.parseInt(day1.substring(3,5)));
                endDate = LocalDate.of(Integer.parseInt(day2.substring(6,10)),Integer.parseInt(day2.substring(0,2)),Integer.parseInt(day2.substring(3,5))+1);
                dates = startDate.datesUntil(endDate).collect(Collectors.toList());
                notValid = false;
            } catch (Exception e) {
                System.out.println("Possible errors! Check the following: ");
                System.out.println("Must be in format (mm/dd/yyyy)... i.e. 07/02/2020");
                System.out.println("End date is before start date");
                System.out.println("Exception Error: "+ e);
            }
        }

        return dates;


    }

    public static List<Account> readFile(Scanner sc, List<Account> accounts){

        Account account;
        String billType = sc.next();
        String billName = "";
        int billDate = 0;
        double billAmount =0;
        double balanceAmount=0;
        double newBillAmount=0;


        while(!billType.equals("0"))
        {
//            BalanceAndBill Cellphone 15 69.01 Balance 733.59 PartialBill 46.78
//            Bill AmazonBundle 16 11.28 NA
//            Balance BestBuy 12 100.00 Balance 500.00
//            Bill CarInsurance 1/26 816.00 NA
//            Bill CarInsurance2 7/26 816.00 NA
//            Bill CrunchyRoll 30 9.02 NA
//            0


            try {
                billName = sc.next();
                billDate = sc.nextInt();
                billAmount = sc.nextDouble();


                if(billType.equalsIgnoreCase("BalanceAndBill")){
                    balanceAmount = sc.nextDouble();
                    newBillAmount = sc.nextDouble();
                    account = new Account("Balance and Bill", billName, billDate, billAmount, balanceAmount, newBillAmount);
                }
                // Bill
                else if(billType.equalsIgnoreCase("Balance")){
                    balanceAmount = sc.nextDouble();
                    account = new Account(billType, billName, billDate, billAmount, balanceAmount);
                }
                else{
                    account = new Account(billType, billName, billDate, billAmount);
                }
            }catch(Exception e) {
                // if "special" date (when payment only occurs on a specific day of the year)
                String[] stringDate = sc.next().split("/");
                int month = Integer.parseInt(stringDate[0]);
                int date = Integer.parseInt(stringDate[1]);

                billAmount = sc.nextDouble();


                // at this point there is only one scan to be done .... sc.nextDouble for bill..depends on case

                if(billType.equalsIgnoreCase("BalanceAndBill")){
                    balanceAmount = sc.nextDouble();
                    newBillAmount = sc.nextDouble();
                    account = new Account("Balance and Bill", billName, date, billAmount, balanceAmount, newBillAmount, month);
                }
                // Bill
                else if(billType.equalsIgnoreCase("Balance")){
                    balanceAmount = sc.nextDouble();
                    account = new Account(billType, billName, date, billAmount, balanceAmount, month);
                }
                else{
                    account = new Account(billType, billName, billDate, billAmount ,month);
                }
            }
            if(account.getDate() > 28){
                System.out.println("You're bill date will be changed to the 28th for "+account.getName()+"!");
                account.setDate(28);
            }
            accounts.add(account);
            // keep reading first string of line until "0"
            billType = sc.next();

        }


       // MAKE METHOD FOR ONLY PRINTING ACCOUNT
        System.out.println("\nYou have the following accounts: ");
        for(Account x : accounts)
        {
            if(x.getMonth()!=0){
                if(x.getType().equalsIgnoreCase("BalanceAndBill")){
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: "+ x.getMonth()+"/"+ x.getDate() +
                            " Remaining Balance: "+x.getBalance() +"\n", x.getCost());
                }
                // Bill
                else if(x.getType().equalsIgnoreCase("Balance")){
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: "+ x.getMonth()+"/"+ x.getDate() + "\n", x.getCost());
                }
                else{
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: "+ x.getMonth()+"/"+ x.getDate() + "\n", x.getCost());
                }
            }else {
                if(x.getType().equalsIgnoreCase("BalanceAndBill")){
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: " + x.getDate() + "\n", x.getCost());
                }
                // Bill
                else if(x.getType().equalsIgnoreCase("Balance")){
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: " + x.getDate() + "\n", x.getCost());
                }
                else{
                    System.out.printf("Bill Type: " + x.getType() + " Name: " + x.getName() + "\t\t Cost: $%.2f\t\t Date: " + x.getDate() + "\n", x.getCost());
                }

            }
        }
        System.out.println("=======================================");


        return accounts;

    }

}
