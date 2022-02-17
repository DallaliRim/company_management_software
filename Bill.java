public class Bill implements Payable {
    private String company_name;
    private double amount_to_be_paid;
    private Date due_date;

    /**
     * Constructor
     * @param company_name
     * @param amount_to_be_paid
     * @param due_date
     */
    public Bill(String company_name, double amount_to_be_paid, Date due_date) {
        this.company_name = company_name;
        this.amount_to_be_paid = amount_to_be_paid;
        this.due_date = due_date;
    }

    /**
     * This method returns a new Cheque for the bill calling the pay method.
     * @return Cheque
     */
    public Cheque pay() {
        return new Cheque(this.company_name, this.amount_to_be_paid);
    }

    @Override
    public String toString() {
        return "\nBILL ---- " + this.company_name + "\nAmount to be paid : " + this.amount_to_be_paid + "\nDue date : " + this.due_date
        + "\n----------------------------------";
    }
}
