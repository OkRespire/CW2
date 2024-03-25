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
            System.exit(0); //if the input is not an integer, exit the program
        }

        //inputting customer details
        for (int i = 0; i < amount; i++) {
            try {
                System.out.println("Enter Customer ID");
                String custID = in.nextLine();

                if (custID.length() != 6 || !custID.matches(regex)) {
                    throw new IllegalArgumentException("Invalid Customer ID"); //if the customer ID is not in the correct format, it will throw an error
                }

                System.out.println("Enter Customer Income");
                double custIncome = in.nextDouble();
                in.nextLine();

                if(custIncome < 0) {
                    throw new IllegalArgumentException("Invalid Customer Income");
                }
                Customer tempCust = new Customer(custID, custIncome);//creates a new customer and puts it in a temporary class

                customerArray.add(tempCust); //adds the temporary class to the customer array
                customerIds.put(custID, tempCust);//allows the customer to be accessed by their ID for future use

            } catch (Exception e) {

                System.err.println(e.getMessage());
                i--;
            }
        }

        for (int i = 0; i < amountOfRecords; i++) {
            String query;
            System.out.println("What customer ID do you want to add a record for or type None to exit"); //asks the user which customer they want to add a record for
            query = in.nextLine();

            if (query.equals("None")) { //if the user types "None", exit the program
                break;
            } else if (!customerIds.containsKey(query)) {
                System.err.println("Invalid Customer ID, try again.");//if the customer ID is not in the correct format, or it is not in the map, it will throw an error
                i--;
                continue;
            }

            Customer cust = customerIds.get(query); //gets the customer from the customer ID

            do {
                try {
                    System.out.println("1. Personal Loan\n2. Builder Loan\n3. Auto Loan\n4. Mortgage Loan\n5. Other\n6. Exit"); //asks the user what type of loan they want to add
                    menuOption = in.nextInt();
                    in.nextLine();


                    recordID = inputRecID(in, usedRecords); //input the record ID
                    usedRecords.add(recordID); //adds the recordID to the set of used records - this allows us to only keep track of unique records
                    thouAmount = inputAmount(in); //input the amount of thousands left to pay
                    interest = inputInterest(in); //input the interest rate
                    duration = inputDuration(in); //input the duration of the loan
                    in.nextLine();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    i--;
                }
                try {
                    processLoan(in, menuOption, recordID, thouAmount, interest, duration, cust, totalRecords);//processes the loan - more detail in the actual function.
                    totalRecords++;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    usedRecords.remove(recordID); //removes the recordID from the set of used records
                    totalRecords--;//decrements the total records
                    i--;
                }

                if (!usedRecords.isEmpty()) { //if the set of used records is not empty...
                    System.out.println("Would you like to quit adding onto this customer? (True/False)");//...ask the user if they want to quit adding onto the specific customer
                    flag = in.nextBoolean();
                    in.nextLine();
                    if (flag) {
                        break; //if the user wants to quit, break out of the loop
                    }
                }
            } while (!flag || amountOfRecords != totalRecords);


        }

        while (true) {
            flag = false;
            printTable(totalRecords, amountOfRecords, customerArray); //prints out the table

            System.out.println("\nWhat would you like to do:\n1. Edit a field\n2. Delete a record\n3. Add a new record/customer\n4. Exit");//asks the user what they want to do

            menuOption = in.nextInt();
            in.nextLine();
            switch (menuOption) {
                case 1: //if the user wants to edit a field, they type 1
                    while (true) {
                        if(flag) break;
                        Customer tempCust = null; //creates a temporary customer
                        try {
                            if(flag) break;

                            String custID;
                            System.out.println("Enter the CustomerID");//the user inputs the customerID
                            custID = in.nextLine();

                            if (!custID.matches(regex)) {
                                throw new IllegalArgumentException("Invalid CustomerID");
                            }
                            tempCust = customerIds.get(custID); //gets the customer from the customer ID
                            System.out.println("Enter the RecordID");
                            recordID = in.nextLine(); //input the record ID
                            flag = true;
                            if (!usedRecords.contains(recordID)) {
                                throw new IllegalArgumentException("Invalid RecordID");
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                            continue;//if the customerID is not in the correct format, or it is not in the map/if the recordID is not in the set, it will throw an error
                        }
                        System.out.println("What field would you like to modify?\n1. Amount\n2. Interest\n3. Duration\n4. Exit");
                        int option = in.nextInt();
                        in.nextLine();
                        switch (option) {
                            case 1: //if the user wants to edit the amount, they type 1
                                while (true) {
                                    try {
                                        thouAmount = inputAmount(in);
                                        in.nextLine();
                                        assert tempCust != null;
                                        tempCust.getLoan(recordID).setThouAmount(thouAmount); //sets the amount of thousands left to pay
                                        break;
                                    } catch (Exception e) {
                                        System.err.println(e.getMessage());
                                    }
                                }
                                break;
                            case 2://if the user wants to edit the interest, they type 2
                                while (true) {
                                    try {
                                        interest = inputInterest(in);
                                        in.nextLine();
                                        assert tempCust != null;
                                        tempCust.getLoan(recordID).setInterest(interest); //sets the interest
                                        break;

                                    } catch (Exception e) {
                                        System.err.println(e.getMessage());
                                        continue;
                                    }
                                }
                                break;
                            case 3://if the user wants to edit the duration, they type 3
                                try {
                                    duration = inputDuration(in);
                                    in.nextLine();

                                    assert tempCust != null;
                                    tempCust.getLoan(recordID).setDuration(duration);//sets the duration
                                } catch (Exception e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }

                                break;
                            case 4://if the user wants to exit, they type 4
                                System.exit(0);
                                break;
                            default:
                                System.err.println("Invalid option, try again.");
                                break;
                        }


                    }
                case 2://if the user wants to delete a record, they type 2
                    while (true) {
                        if(flag) break;
                        System.out.println("Enter the CustomerID");
                        String custID = in.nextLine();
                        if (!custID.matches(regex) || !customerIds.containsKey(custID)) { //checks if the customer ID is in the correct format and if it is in the map
                            System.err.println("Invalid CustomerID"); //if not, it will throw an error
                            continue;
                        }
                        flag = true;

                        Customer tempCust = customerIds.get(custID); //gets the customer from the customer ID

                        do {
                            System.out.println("Enter the Record ID"); //the user inputs the record ID
                            recordID = in.nextLine();
                            if (usedRecords.contains(recordID)) { //checks if the record ID is in the set
                                tempCust.remove(tempCust.getLoan(recordID)); //if it is, it will remove it from the customer
                                totalRecords--;//decreases the total records by 1
                                break;
                            } else System.out.println("Invalid Record ID"); //otherwise, it will throw an error

                        } while (true);
                        break;
                    }
                    break;
                case 3:
                    while (true) {
                        if(flag) break;
                        if (amountOfRecords == totalRecords) { //if the amount of records is equal to the total records...
                            System.out.println("Maximum number of records reached"); //...it will throw an error
                            break;
                        }
                        boolean decision = true;
                        if (customerArray.size() != amount || decision)  //if the amount of customers is not equal to the amount of customers in the array or if the user wants to create a new customer
                            System.out.println("Would you like to create a new customer? (True/False)");
                        decision = in.nextBoolean();//asks if the user wants to create a new customer
                        in.nextLine();
                        if (decision) { //creates a new customer if decision is true
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
                            flag = true;
                            Customer newCust = new Customer(custID, custIncome);
                            customerIds.put(custID, newCust);//adds the new customer to the map
                            customerArray.add(newCust);//adds the new customer to the array
                            amount++;//increases the amount of customers by 1
                        }
                        System.out.println("Which customer would you like to add a loan to?");
                        String custID = in.nextLine();//asks which customer the user wants to add a loan to

                        if (!custID.matches(regex)) {
                            System.err.println("Invalid CustomerID");
                            continue;
                        }
                        Customer cust = customerIds.get(custID); //gets the customer from the customer ID

                        while (true) {
                            try {
                                System.out.println("1. Personal Loan\n2. Builder Loan\n3. Auto Loan\n4. Mortgage Loan\n5. Other\n6. Exit");
                                menuOption = in.nextInt(); //asks which loan the user wants to add
                                in.nextLine();
                                recordID = inputRecID(in, usedRecords); //gets the record ID
                                usedRecords.add(recordID);//adds the record ID to the set
                                System.out.println("Enter the Amount left to pay (in thousands)");
                                thouAmount = in.nextInt();//gets the amount left to pay
                                in.nextLine();
                                flag = true;
                                interest = inputInterest(in); //gets the interest
                                duration = inputDuration(in); //gets the duration
                                break;
                            } catch (Exception e) {
                                System.err.println(e.getMessage());//if any input is wrong, it will throw an error
                            }
                            try {
                                processLoan(in, menuOption, recordID, thouAmount, interest, duration, cust, totalRecords);
                                amountOfRecords++;//increases the amount of records by 1
                            }catch (Exception e) {
                                System.err.println(e.getMessage());
                                amountOfRecords--;//decrements the amount of records by 1
                                usedRecords.remove(recordID); //removes the record ID from the set so you can use it again
                            }
                        }
                    }
                    break;
                case 4:
                    System.exit(0);//exits the program
                    break;
            }
        }
    }

    public static void processLoan(Scanner in, int menuOption, String recordID, int thouAmount, double interest, int duration, Customer cust, int totalRecords) {
        switch (menuOption) {
            case 1:
                Personal personalLoan = new Personal(recordID, thouAmount, interest, duration); //creates a new personal loan
                cust.add(personalLoan);//adds the loan to the customer
                break;
            case 2:
                try {
                    double overpay;//the user inputs the overpay
                    do {
                        System.out.println("Enter the Overpay");//the user inputs the overpay
                        overpay = in.nextDouble();

                        if (overpay > 2 || overpay < 0) {
                            throw new IllegalArgumentException("Invalid Overpay");//if the overpay is not between 0 and 2, it will throw an error
                        }
                    } while (!(overpay <= 2) && !(overpay >= 0));

                    Builder buildLoan = new Builder(recordID, thouAmount, interest, duration, overpay);//creates a new builder loan
                    cust.add(buildLoan);

                } catch (Exception b) {
                    System.err.println(b.getMessage());
                }
                break;
            case 3:
                Auto autoLoan = new Auto(recordID, thouAmount, interest, duration);//creates a new auto loan
                cust.add(autoLoan);
                break;
            case 4:
                try {
                    System.out.println("Enter the Overpay");//the user inputs the overpay
                    double overpay = in.nextDouble();
                    if (overpay > 2 || overpay < 0) {
                        throw new IllegalArgumentException("Invalid Overpay");
                    }
                    Mortgage mortLoan = new Mortgage(recordID, thouAmount, interest, duration, overpay);//creates a new mortgage loan
                    cust.add(mortLoan);//adds the loan to the customer

                } catch (Exception a) {
                    System.err.println(a.getMessage());
                }
                break;
            case 5:
                Other otherLoan = new Other(recordID, thouAmount, interest, duration);//creates a new other loan
                cust.add(otherLoan);
                break;
            case 6:
                break;
            default:
                System.err.println("Invalid option, try again.");//if the user inputs an invalid option, it will throw an error
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
        System.out.printf("Maximum number of records: %d\n", amountOfRecords); //prints out how many items can be inserted...
        System.out.printf("Registered records: %d\n", totalRecords);//... and how many have been inserted

        System.out.println("--------------------------------"); //template from https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-print-table-format-printf-chart-console-scanner-println-line
        System.out.println("Loan Management System");
        System.out.println("--------------------------------");

        for (Customer c : customerArray) {
            c.printTable();
            System.out.println("---------------------------------------------------------------------------------\n");
        }
    }

}

