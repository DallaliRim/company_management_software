public abstract class Employee implements Payable {
    public String first_name;
    public String last_name;
    public int age;
    public static int employee_count = 0;
    public int employee_id;
    
    /**
     * Constructor
     * @param first_name
     * @param last_name
     * @param age
     */
    public Employee(String first_name, String last_name, int age) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.employee_id = employee_count ++;
    }

    /**
     * This method allows access to the specified employee's name
     * @return String
     */
    public String get_employee_name() {
        return this.last_name + ", " + this.first_name;
    }
}
