public class Mortgage extends Loan {


    protected double overpay;

    //default constructor
    public Mortgage() {
        super();
        this.loanType = "Mortgage";
        this.overpay = 0.5;
    }

    //custom constructor
    public Mortgage(String RecordID, int thouAmount, double interest, int duration, double overpay) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Mortgage"; //sets loan type
        this.overpay = overpay;//sets overpay
    }


}

