public class Mortgage extends Loan {


    protected double overpay;
    public Mortgage(String RecordID, int thouAmount,double interest, int duration, double overpay) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Mortgage";
        this.overpay = overpay;
    }


}

