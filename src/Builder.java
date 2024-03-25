public class Builder extends Loan {

    protected double overpay;

    //default constructor
    public Builder() {
        super();
        this.loanType = "Builder";
        this.overpay = 0.5;
    }

    //custom constructor
    public Builder(String RecordID, int thouAmount, double interest, int duration, double overpay) {
        super(RecordID, thouAmount, interest, duration);
        this.loanType = "Builder"; //sets loan type
        this.overpay = overpay;//sets overpay

    }


}

