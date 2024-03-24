import java.util.*;

public class XYZBank {
    public static void main(String[] args) {

        //initializing variables
        int menuOption = 0;
        String recordID = null;
        double interest = 0;
        int duration = 0;
        int thouAmount = 0;
        ArrayList<Customer> customerArray = new ArrayList<>();
        int amount = 0, amountOfRecords = 0, totalRecords = 0;
        Scanner in = new Scanner(System.in);
        boolean flag = false;
        Map<String, Customer> customerIds = new HashMap<>();
        Set<String> usedRecords = new HashSet<>();
        //initialising regex to check the inputs of the user
        String regex = "[A-Z]{3}[0-9]{3}";

        //getting user input - about how many customers and records they want to input
        try {
            System.out.println("How many customers do you want to input");
            amount = in.nextInt();
            in.nextLine();

            System.out.println("How many records do you want to input");
            amountOfRecords = in.nextInt();
            in.nextLine();

        } catch (InputMismatchException e) {
            System.err.println("Must be an integer");
            System.exit(0);
        }


        for (int i = 0; i < amount; i++) {
            try {
                System.out.println("Enter Customer ID");
                String custID = in.nextLine();

                if (custID.length() != 6 || !custID.matches(regex)) {
                    throw new IllegalArgumentException("Invalid Customer ID");
                }

                System.out.println("Enter Customer Income");
                double custIncome = in.nextDouble();
                in.nextLine();
                Customer tempCust = new Customer(custID, custIncome);//creates a new customer and puts it in a temporary class

                customerArray.add(tempCust);
                customerIds.put(custID, tempCust);

            } catch (Exception e) {

                System.err.println(e.getMessage());
                i--;
            }
        }

        for (int i = 0; i < amountOfRecords; i++) {
            String query;
            System.out.println("What customer ID do you want to add a record for or type None to exit");
            query = in.nextLine();

            if(query.equals("None")){
                break;
            }
            else if (!customerIds.containsKey(query)) {
                System.err.println("Invalid Customer ID, try again.");
                i--;
                continue;
            }

            Customer cust = customerIds.get(query);

            do {
                try {
                    System.out.println("1. Personal Loan\n2. Builder Loan\n3. Auto Loan\n4. Mortgage Loan\n5. Other\n6. Exit");
                    menuOption = in.nextInt();
                    in.nextLine();


                    recordID = inputRecID(in, usedRecords);
                    usedRecords.add(recordID);
                    thouAmount = inputAmount(in);
                    interest = inputInterest(in);
                    duration = inputDuration(in);
                    in.nextLine();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    i--;
                }
                try {
                    processLoan(in, menuOption, recordID, thouAmount, interest, duration, cust, totalRecords);
                    totalRecords++;
                }catch(Exception e){
                    System.err.println(e.getMessage());
                    i--;
                }

                if (!usedRecords.isEmpty()) {
                    System.out.println("Would you like to quit adding onto this customer? (True/False)");
                    flag = in.nextBoolean();
                    in.nextLine();
                    if (flag) {

                        break;
                    }
                }
            } while (!flag || amountOfRecords != totalRecords);


        }
        printTable(totalRecords, amountOfRecords, customerArray);

        while (true) {

            System.out.println("What would you like to do:\n1. Edit a field\n2. Delete a record\n3. Add a new record/customer\n4. Exit");

            menuOption = in.nextInt();
            in.nextLine();
            switch (menuOption) {
                case 1:
                    while (true) {
                        Customer tempCust = null;
                        try {
                            String custID;
                            System.out.println("Enter the CustomerID");
                            custID = in.nextLine();

                            if (!custID.matches(regex)) {
                                throw new IllegalArgumentException("Invalid CustomerID");
                            }
                            tempCust = customerIds.get(custID);
                            System.out.println("Enter the RecordID");
                            recordID = inputRecID(in, usedRecords);
                            usedRecords.add(recordID);

                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                        System.out.println("What field would you like to modify?\n1. Amount\n2. Interest\n3. Duration\n4. Exit");
                        int option = in.nextInt();
                        in.nextLine();
                        switch (option) {
                            case 1:
                                while (true) {
                                    try {
                                        thouAmount = inputAmount(in);
                                        in.nextLine();
                                        assert tempCust != null;
                                        tempCust.getLoan(recordID).setThouAmount(thouAmount);
                                    } catch (Exception e) {
                                        System.err.println(e.getMessage());
                                        continue;
                                    }

                                    break;
                                }
                                break;
                            case 2:
                                while (true) {
                                    try {
                                        interest = inputInterest(in);
                                        in.nextLine();
                                        assert tempCust != null;
                                        tempCust.getLoan(recordID).setInterest(interest);

                                    } catch (Exception e) {
                                        System.err.println(e.getMessage());
                                        continue;
                                    }
                                    break;
                                }
                                break;
                            case 3:

                                try {
                                    duration = inputDuration(in);
                                    in.nextLine();

                                    assert tempCust != null;
                                    tempCust.getLoan(recordID).setDuration(duration);
                                } catch (Exception e) {
                                    System.err.println(e.getMessage());
                                }

                                break;
                            case 4:
                                System.exit(0);
                                break;
                            default:
                                System.err.println("Invalid option, try again.");
                                break;
                        }


                    }
                case 2:
                    while (true) {
                        System.out.println("Enter the CustomerID");
                        String custID = in.nextLine();
                        if (!custID.matches(regex)) {
                            System.err.println("Invalid CustomerID");
                            continue;
                        }

                        Customer tempCust = customerIds.get(custID);

                        do{
                            System.out.println("Enter the Record ID");
                            recordID = in.nextLine();
                            if(usedRecords.contains(recordID)){
                                tempCust.remove(tempCust.getLoan(recordID));
                                totalRecords--;
                                break;
                            }else System.out.println("Invalid Record ID");

                        }while(true);
                        break;
                    }
                    break;
                case 3:
                    while (true) {
                        if (amountOfRecords == totalRecords) {
                            System.out.println("Maximum number of records reached");
                            break;
                        }
                        boolean decision = true;
                        if (customerArray.size() != amount || decision) {
                            System.out.println("Would you like to create a new customer? (True/False)");
                            decision = in.nextBoolean();
                            in.nextLine();
                            if (decision) {
                                System.out.println("Enter the CustomerID");
                                String custID = in.nextLine();
                                if (!custID.matches(regex)) {
                                    System.err.println("Invalid CustomerID");
                                    continue;
                                }
                                System.out.println("Enter the Customer Income");
                                double custIncome = in.nextDouble();
                                in.nextLine();
                                if (custIncome < 0) {
                                    System.err.println("Invalid Income");
                                    continue;
                                }
                                Customer newCust = new Customer(custID, custIncome);
                                customerIds.put(custID, newCust);
                                customerArray.add(newCust);
                                amount++;
                            }
                            System.out.println("Which customer would you like to add a loan to?");
                            String custID = in.nextLine();

                            if (!custID.matches(regex)) {
                                System.err.println("Invalid CustomerID");
                                continue;
                            }
                            Customer cust = customerIds.get(custID);

                            while (true) {


                                try {
                                    System.out.println("1. Personal Loan\n2. Builder Loan\n3. Auto Loan\n4. Mortgage Loan\n5. Other\n6. Exit");
                                    menuOption = in.nextInt();
                                    in.nextLine();
                                    recordID = inputRecID(in, usedRecords);
                                    usedRecords.add(recordID);
                                    System.out.println("Enter the Amount left to pay (in thousands)");
                                    thouAmount = in.nextInt();
                                    in.nextLine();
                                    if (thouAmount < 0) {
                                        throw new IllegalArgumentException("Invalid Amount");
                                    }

                                    System.out.println("Enter the Interest");
                                    interest = in.nextDouble();

                                    if (interest < 0 || interest > 100) {
                                        throw new IllegalArgumentException("Invalid Interest");
                                    }
                                    System.out.println("Enter the duration in years. Must be between 0 and 30");
                                    duration = in.nextInt();
                                    if (duration < 0 || duration > 30) {
                                        throw new IllegalArgumentException("Invalid Duration");
                                    }
                                    break;
                                } catch (Exception e) {
                                    System.err.println(e.getMessage());
                                }
                                processLoan(in,menuOption, recordID, thouAmount, interest, duration, cust, totalRecords);



                            }


                        }
            }
            break;

        }
            printTable(totalRecords, amountOfRecords, customerArray);
        }

        }
    public static void processLoan(Scanner in,int menuOption, String recordID, int thouAmount, double interest, int duration, Customer cust, int totalRecords) {


        switch (menuOption) {
            case 1:
                Personal personalLoan = new Personal(recordID, thouAmount, interest, duration);
                cust.add(personalLoan);
                break;
            case 2:
                try {
                    double overpay;
                    do {
                        System.out.println("Enter the Overpay");
                        overpay = in.nextDouble();

                        if (overpay > 2 || overpay < 0) {
                            throw new IllegalArgumentException("Invalid Overpay");
                        }
                    } while (!(overpay <= 2) && !(overpay >= 0));

                    Builder buildLoan = new Builder(recordID, thouAmount, interest, duration, overpay);
                    totalRecords++;
                    cust.add(buildLoan);

                } catch (Exception b) {
                    System.err.println(b.getMessage());
                }
                break;
            case 3:
                Auto autoLoan = new Auto(recordID, thouAmount, interest, duration);
                cust.add(autoLoan);
                break;
            case 4:
                try {
                    System.out.println("Enter the Overpay");
                    double overpay = in.nextDouble();
                    if (overpay > 2 || overpay < 0) {
                        throw new IllegalArgumentException("Invalid Overpay");
                    }
                    Mortgage mortLoan = new Mortgage(recordID, thouAmount, interest, duration, overpay);
                    cust.add(mortLoan);

                } catch (Exception a) {
                    System.err.println(a.getMessage());
                }
                break;
            case 5:
                Other otherLoan = new Other(recordID, thouAmount, interest, duration);
                cust.add(otherLoan);
                break;
            case 6:
                break;
            default:
                System.err.println("Invalid option, try again.");
                break;
        }
    }

    private static String inputRecID(Scanner in, Set<String> usedRecords) {
        String recID;
        do {
            System.out.println("Enter the Record ID: "); //user inputs a recordID
            recID = in.nextLine();

            if (recID.length() != 6) //The record must follow these conditions, otherwise the program throws an error
                System.err.println("RecordID invalid. Make sure that it is 6 characters long. Please re-enter");
            else if (!recID.matches("[0-9]{6}")) //checks if the input matches the pattern of having numbers 6 times in a row
                System.err.println("RecordID invalid. Make sure that it is in the format 000000. Please re-enter");


            else if (usedRecords.contains(recID)) //if a recordID is already present in the set defined earlier, it will throw an error and make the user retype the input
                System.err.println("RecordID invalid. Make sure that you add a unique record ID. Please re-enter");


        } while (!recID.matches("[0-9]{6}") || recID.length() != 6 || usedRecords.contains(recID)); //if at least one of these conditions are true,
        // the do while loop does not break
        return recID; //returns recordID
    }


    private static double inputInterest(Scanner in) {
        double interest = 0;

        //checks if the interest is a positive number that is below 0
        do {

            try {
                System.out.println("Enter the interest rate: ");//asks the user to input a value again
                interest = in.nextDouble();
                if (interest < 0)
                    System.err.println("Interest cannot be below 0. Please re-enter"); //throws an error if it is below 0...
                else if (interest > 100)
                    System.err.println("Interest cannot be above 100. Please re-enter");//...or above 100

            } catch (Exception e) {
                System.err.println("Must be a decimal number");//if it is not a number, it will throw this error and restart the loop
                in.next();//makes it so it does not infinitely loop
            }

        } while (!(interest >= 0 && interest <= 100));
        return interest;
    }

    private static int inputAmount(Scanner in) {
        int amount = 0;
        //checks if the value is a positive number
        do {

            try {
                System.out.println("Enter the amount left to pay (in thousands): ");//...asks the user to input a value
                amount = in.nextInt();
                if (amount <= 0)
                    System.err.println("The amount left to pay cannot be below 0 or be 0");//throws an error
            } catch (Exception e) {
                System.err.println("Must be an integer"); //same principle as checking if the interest is a number
                in.next();
            }
        } while (!(amount > 0));
        return amount;
    }

    private static int inputDuration(Scanner in) {

        int duration = 0;
        do {

            try {
                System.out.println("Enter the years left to pay the loan back: ");
                duration = in.nextInt();
                if (!(duration > 0))
                    System.err.println("The amount of years cannot be below 0"); //this is the same as the while loop on the inputAmount function


            } catch (Exception e) {
                System.err.println("Must be an integer");
                in.next();
            }
        } while (!(duration > 0));
        return duration;
    }

    public static void printTable(int totalRecords, int amountOfRecords, ArrayList<Customer> customerArray) {
        System.out.printf("Maximum number of records: %d\n", totalRecords); //prints out how many items can be inserted...
        System.out.printf("Registered records: %d\n", amountOfRecords);//... and how many have been inserted

        System.out.println("--------------------------------"); //template from https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-print-table-format-printf-chart-console-scanner-println-line
        System.out.println("Loan Management System");
        System.out.println("--------------------------------");

        for (Customer c : customerArray) {
            c.printTable();
            System.out.println("---------------------------------------------------------------------------------\n");
        }
    }
    }
