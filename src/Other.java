public class Other extends Loan {

    public Other() {
        super();
        this.loanType = "Other";
    }

    public Other(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Other";
    }


}
