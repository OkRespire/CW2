public class Other extends Loan {

    //default constructor
    public Other() {
        super();
        this.loanType = "Other";
    }
   //custom constructor
    public Other(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Other";//set loan type
    }


}
