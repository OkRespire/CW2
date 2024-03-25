public abstract class Loan {

    protected String RecordID;
    protected String loanType;
    protected double interest;

    protected int thouAmount;
    protected int duration;


    public Loan(String RecordID, int thouAmount, double interest, int duration) {
        this.RecordID = RecordID;
        this.thouAmount = thouAmount;
        this.interest = interest;
        this.duration = duration;
    }

    public Loan() {
        this.RecordID = "000001";
        this.thouAmount = 4;
        this.interest = 2.3;
        this.duration = 3;
    }

    @Override
    public String toString() {
        return String.format(" %-10s  %-10s  %-10f  %-10d  %-10d %n", getRecordID(), getLoanType(), getInterest(), getThouAmount(), getDuration());
    }

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
