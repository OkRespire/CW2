import java.util.ArrayList;


public class Customer implements CheckerPrinter {
    protected String custID;
    protected double custIncome;
    protected boolean eligible = true;

    protected ArrayList<Loan> loanRecs = new ArrayList<>();

    public Customer() {
        this.custID = "AAA001";
        this.custIncome = 50000;
    }

    public Customer(String custID, double custIncome) {
        this.custID = custID;
        this.custIncome = custIncome;
    }

    public String getCustID() {
        return custID;
    }

    public double getCustIncome() {
        return custIncome;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public void setCustIncome(double custIncome) {
        this.custIncome = custIncome;
    }


    public String yesNo(boolean b){
        if(b){
            return "Yes";
        }else{
            return "No";
        }
    }


    @Override
    public boolean eligible() {
        return false;
    }

    @Override
    public boolean eligible(double amount) {
        int amount_ = 0;
        if(loanRecs.isEmpty()){
            eligible = (amount*1000) < (custIncome * 4);
            return eligible;
        }
        for(Loan loan : loanRecs){
            amount_ += loan.thouAmount*1000;
        }
        eligible = amount_ < (custIncome * 4);
        return eligible;
    }

    @Override
    public void printTable() {

        System.out.println("Customer ID: " + custID + "\nEligible to arrange new loans: " + yesNo(eligible) + "\n");
        System.out.printf(" %-8s  %-8s  %-8s  %-8s  %-8s%n", "RECORDID", "TYPE OF LOAN", "INTEREST", "AMOUNT LEFT", "YEARS LEFT"); //sets up a table
        for(Loan loan : loanRecs){
            System.out.println(loan.toString());
        }

    }

    public void add(Loan loan) {
        if (eligible(loan.thouAmount)) {
            loanRecs.add(loan);
        } else {
            System.err.println("Customer is not eligible");
        }
    }


    public Loan getLoan(String recordID) {
            for(Loan loan : loanRecs) {
                if(loan.getRecordID().equals(recordID)) {
                    return loan;
                }
        }
        return null;
    }

    public void remove(Loan loan) {
        loanRecs.remove(loan);
    }


}