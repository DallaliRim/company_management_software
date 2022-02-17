import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private ArrayList<Payable> payables = new ArrayList<Payable>();
    private ArrayList<Cheque> cheques = new ArrayList<>();  

    /**
     * This method adds a full time employee to the payables based on the given inputs :
     * @param monthly_salary
     * @param first_name
     * @param last_name
     * @param age
     */
    public void add_employee(double monthly_salary, String first_name, String last_name, int age) { 
        payables.add(new FullTimer(monthly_salary, first_name, last_name, age));
    }

    /**
     * This method adds a part time employee to the payables based on the given inputs :
     * @param echelon
     * @param hours_worked
     * @param first_name
     * @param last_name
     * @param age
     */
    public void add_employee(int echelon, double hours_worked, String first_name, String last_name, int age) {
        payables.add(new PartTimer(hours_worked, echelon, first_name, last_name, age));
    }

    /**
     * This method adds a bill to the payables arraylist
     * @param company_name
     * @param amount_to_be_paid
     * @param due_date
     */
    public void add_bill(String company_name, double amount_to_be_paid, Date due_date) {
        payables.add(new Bill(company_name, amount_to_be_paid, due_date));
    }

    /**
     * This method is in charge of reading information from the given file (hard-coded)
     * And adds all the employees to the payables
     * @return ArrayList<Payable>
     * @throws IOException
     */
    public ArrayList<Payable> load_employees_from_file() throws IOException {
        Path p = Paths.get("employees_list.txt");
        List<String> employees_list = Files.readAllLines(p); 

        for(String line : employees_list){
            String[] info = line.split(";");
            if(info[3].equals("P")) {
                Payable employee = new PartTimer(Integer.parseInt(info[5]), Integer.parseInt(info[4]), info[0], info[1], Integer.parseInt(info[2]));
                payables.add(employee);
            }
            else if(info[3].equals("F")) {
                Payable employee = new FullTimer(Integer.parseInt(info[4]), info[0], info[1], Integer.parseInt(info[2]));
                payables.add(employee);
            }
            else {
                throw new IllegalArgumentException("The data format in the employees list is wrong, please check it");
            }
        }
        return this.payables;
    }

    /**
     * This method returns a String containing all employees in the payables arraylist
     * This method uses instance of in order to differentiate between bill and employees
     * @return String
     */
    public String show_all_employees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Payable p : payables) {
            if(p instanceof Employee) {
                employees.add((Employee)p);
            }
        }
        return employees.toString();
    }

    /**
     * This method returns a String containing all bills in the payables arraylist
     * This method uses instance of in order to differentiate between bill and employees
     * @return String
     */
    public String show_all_bills() {
        ArrayList<Bill> bills = new ArrayList<>();
        for (Payable p : payables) {
            if(p instanceof Bill) {
                bills.add((Bill)p);
            }
        }
        return bills.toString();
    }

    /**
     * This method issues the cheques for each payable by calling the pay method on each payable
     */
    public void issue_cheques() {
        for (Payable payable : payables) {
            Cheque cheque = payable.pay();
            cheques.add(cheque);
        }
    }

    /**
     * returns a String containing all information pertaining to the Cheques
     * @return String
     */
    public String show_all_cheques() {
        String str_cheques = "";
        for (Cheque cheque : this.cheques) {
            str_cheques += cheque.get_display();
        }
        return str_cheques;
    }
}