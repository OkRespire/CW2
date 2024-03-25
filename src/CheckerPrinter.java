public interface CheckerPrinter {

    boolean eligible();//checks if the loan is eligible - not used much

    String toString();//returns a string


    boolean eligible(double amount);//checks if the loan is eligible

    void printTable();//prints the table


}
