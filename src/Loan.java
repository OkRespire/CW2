public abstract class Loan {

    // attributes
    protected String RecordID;
    protected String loanType;
    protected double interest;

    protected int thouAmount;
    protected int duration;


    // Constructor
    public Loan(String RecordID, int thouAmount, double interest, int duration) {
        // sets attributes
        this.RecordID = RecordID;
        this.thouAmount = thouAmount;
        this.interest = interest;
        this.duration = duration;
    }


    //default constructor
    public Loan() {
        this.RecordID = "000001";
        this.thouAmount = 4;
        this.interest = 2.3;
        this.duration = 3;
    }

    @Override
    public String toString() { // for printing the loan's details
        return String.format(" %-10s  %-10s  %-10f  %-10d  %-10d %n", getRecordID(), getLoanType(), getInterest(), getThouAmount(), getDuration());
    }


    // getters and setters
    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLoanType() {
        return loanType;
    }

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public int getThouAmount() {
        return thouAmount;
    }

    public void setThouAmount(int thouAmount) {
        this.thouAmount = thouAmount;
    }
}
