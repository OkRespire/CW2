public class Personal extends Loan {


    public Personal() {
        super();
        this.loanType = "Personal";
    }

    public Personal(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Personal";
    }


}


