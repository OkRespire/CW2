public class Personal extends Loan {


    //default constructor
    public Personal() {
        super();
        this.loanType = "Personal";
    }

    //custom constructor
    public Personal(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Personal"; //sets the loan
    }


}


