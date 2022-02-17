public class PartTimer extends Employee {
    private double hours_worked;
    private double hourly_rate;
    private int echelon;

    /**
     * Constructor
     * @param hours_worked
     * @param echelon
     * @param first_name
     * @param last_name
     * @param age
     */
    public PartTimer(double hours_worked, int echelon, String first_name, String last_name, int age) { 
        super(first_name, last_name, age);
        this.hours_worked = hours_worked;
        this.echelon = echelon;
        this.employee_id = employee_count;
        set_hourly_rate();
    }

    /**
     * This method sets the hourly rate of the part time employee
     * based on his echelon given during its initialization
     */
    public void set_hourly_rate() {
        for (int i = 1; i < 5; i++) {
            if (this.echelon == i) {
                this.hourly_rate = 10 + (i*5);
            }
        }
        if(this.echelon == 5) {
            this.hourly_rate = 40;
        }
    }

    /**
     * This method returns the monthly pay of a part time employee 
     * based on the amount of hours worked and hourly_rate
     * @return double
     */
    public double calculate_salary() {
        return this.hourly_rate*this.hours_worked;
    }

    /**
     * This method returns a cheque to the employee based on 
     * their name and monthly salary
     * @return Cheque
     */
    public Cheque pay() {
        return new Cheque(this.get_employee_name(), calculate_salary());
    }

    @Override
    public String toString() {
        return "\nPART TIME : Employee ---- " + employee_id + "\nName : " + this.first_name + ", " + this.last_name + "\nage : " 
        + this.age + "\nEchelon : " + this.echelon + " -- hourly rate : " + this.hourly_rate + "\nhours worked : " + this.hours_worked
        + "\n----------------------------------";
    }
}
