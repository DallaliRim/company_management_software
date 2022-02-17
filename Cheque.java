public class Cheque {
    private static int cheque_count = 0; 
    private int cheque_number;
    private String addressee;
    private double amount;

    /**
     * Constructor
     * @param addressee
     * @param amount
     */
    public Cheque(String addressee, double amount) {
        this.addressee = addressee;
        this.amount = amount;
        this.cheque_number = cheque_count++;
    }

    /**
     * This method returns a string containing all the informations necessary for the calling cheque
     * @return String
     */
    public String get_display() { 
        return "\nCheque number : " + cheque_number + "\nAddresse : " + this.addressee + "\nAmount : " + this.amount + "\n-------------------------------";
    }
}
