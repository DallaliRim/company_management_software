public class FullTimer extends Employee {
    private double monthly_salary;

    /**
     * Constructor
     * @param monthly_salary
     * @param first_name
     * @param last_name
     * @param age
     */
    public FullTimer (double monthly_salary, String first_name, String last_name, int age) {
        super(first_name, last_name, age);
        this.monthly_salary = monthly_salary;
        this.employee_id = employee_count;
    }

    /**
     * This method returns a cheque for a Full Time employee
     * based on their monthly salary 
     * @return Cheque
     */
    public Cheque pay() {
        return new Cheque(this.get_employee_name(), monthly_salary);
    }

    @Override
    public String toString() {
        return "\nFULL TIME : Employee ---- " + employee_id + "\nName : " + this.first_name + ", " + this.last_name + "\nage : " 
        + this.age + "\nSalary : " + this.monthly_salary + "\n----------------------------------";
    }
}
