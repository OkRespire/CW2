import java.util.ArrayList;


public class Customer implements CheckerPrinter {
    protected String custID;
    protected double custIncome;
    protected boolean eligible = true;

    protected ArrayList<Loan> loanRecs = new ArrayList<>();

    //default constructor
    public Customer() {
        this.custID = "AAA001";
        this.custIncome = 50000;
    }

    //main constructor
    public Customer(String custID, double custIncome) {
        this.custID = custID;
        this.custIncome = custIncome;
    }

    //getters and setters
    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public double getCustIncome() {
        return custIncome;
    }

    public void setCustIncome(double custIncome) {
        this.custIncome = custIncome;
    }


    //returns yes or no for the print method
    public String yesNo(boolean b) {
        if (b) {
            return "Yes";
        } else {
            return "No";
        }
    }


    @Override
    public boolean eligible() {
        return false; //not used as it is default
    }

    @Override
    public boolean eligible(double amount) { //checks if the total amount of the loans is less than 4 times the income of the customer
        int amount_ = 0;
        if (loanRecs.isEmpty()) {
            eligible = (amount * 1000) < (custIncome * 4);
            return eligible;
        }
        for (Loan loan : loanRecs) {
            amount_ += loan.thouAmount * 1000;
        }
        eligible = amount_ < (custIncome * 4);
        return eligible;
    }

    @Override
    public void printTable() { //prints the rows of the table

        System.out.println("Customer ID: " + custID + "\nEligible to arrange new loans: " + yesNo(eligible) + "\n");
        System.out.printf(" %-8s  %-8s  %-8s  %-8s  %-8s%n", "RECORDID", "TYPE OF LOAN", "INTEREST", "AMOUNT LEFT", "YEARS LEFT"); //sets up table headings
        for (Loan loan : loanRecs) {
            System.out.println(loan.toString());
        }

    }

    public void add(Loan loan) { //adds a loan to the customer
        if (eligible(loan.thouAmount)) { //checks if the customer is eligible to add the loan
            loanRecs.add(loan);
        } else {
            System.err.println("Customer is not eligible"); //if not, throws an error
        }
    }


    public Loan getLoan(String recordID) {
        for (Loan loan : loanRecs) { //loops through the list of loans
            if (loan.getRecordID().equals(recordID)) {//if the recordID matches, return the loan
                return loan;
            }
        }
        return null;//otherwise return null
    }

    public void remove(Loan loan) {//removes a loan
        loanRecs.remove(loan);
    }


}