public class Auto extends Loan {


    public Auto() {
        super();
        this.loanType = "Auto";
    }

    public Auto(String RecordID, int thouAmount, double interest, int duration) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Auto";
    }


}
