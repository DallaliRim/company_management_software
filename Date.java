public class Date {
    private String month;
    private int day;
    private int year;

    /**
     * Constructor
     * @param month
     * @param day
     * @param year
     */
    public Date(String month, int day, int year) {
        if(day > 31 || day < 1) {
            throw new IllegalArgumentException("The day for the input date is invalid, day must be between 1 and 31");
        }
        this.month = month;
        this.day = day;
        this.year = year;
    }

    @Override
    public String toString() {
        return this.month + " " + this.day + " " + this.year;
    }
}
