public class Builder extends Loan {

    protected double overpay;
    public Builder(String RecordID, int thouAmount,double interest, int duration, double overpay) {
        super(RecordID,thouAmount, interest, duration);
        this.loanType = "Builder";
        this.overpay = overpay;

    }


}

