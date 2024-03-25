public class Auto extends Loan {



    //default constructor
    public Auto() {
        super();
        this.loanType = "Auto";
    }

    //custom constructor
    public Auto(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Auto"; //set loan type
    }


}
