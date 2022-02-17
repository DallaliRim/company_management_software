import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*; 
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ApplicationFX extends Application {
    private Company company = new Company();

    public static void main(String[] args) {
        Application.launch(args);
    }   

    @Override
    public void start(Stage stage) throws Exception {
        //main containers
        Group root = new Group();
        HBox hMainContainer = new HBox(10);
        hMainContainer.setPadding(new Insets(0, 10, 0, 10));
        VBox vActionContainer = new VBox(5);
        HBox hAddContainer = new HBox(10);
        VBox vListContainer = new VBox(5);
        vListContainer.setPrefWidth(500);

        //add employee feature
        VBox vAddEmployee = new VBox(5);
        Button btnAddEmployee = new Button("Add Employee");
        vAddEmployee.setPrefWidth(200);
        btnAddEmployee.setPrefWidth(200);
        System.out.println((vAddEmployee.getWidth()));
        Label lbFirstName = new Label("first name : ");
        TextField tfFirstName = new TextField();
        Label lbLastName = new Label("last name : ");
        TextField tfLastName = new TextField();
        Label lbAge = new Label("age : ");
        TextField tfAge = new TextField();
        final ToggleGroup availabilities = new ToggleGroup();
        RadioButton radioFullTime = new RadioButton("Full-Time");
        radioFullTime.setToggleGroup(availabilities);
        radioFullTime.setSelected(true);
        RadioButton radioPartTime = new RadioButton("Part-Time");
        radioPartTime.setToggleGroup(availabilities);
        VBox vSalary = new VBox(5);

        //employee list feature
        Label lbListEmployees = new Label("Employees list"); 
        TextArea taListEmployees = new TextArea();
        taListEmployees.setPrefHeight(234);
        VBox vListEmployees = new VBox(5);

        //add bill feature
        VBox vAddBill = new VBox(21);
        vAddBill.setPrefWidth(300);
        Button btnAddBill = new Button("Add Bill");
        btnAddBill.setPrefWidth(300);
        Label lbCompanyName = new Label("company name : ");
        TextField tfCompanyName = new TextField();
        Label lbAmountToPay = new Label("amount to pay : ");
        TextField tfAmountToPay = new TextField();
        Label lbDueDate = new Label("Due Date : day/month/year");
        HBox hSelectDate = new HBox(10);
        ChoiceBox<Integer> cbDay = new ChoiceBox<>();
        cbDay.setValue(1);
        cbDay.getItems().addAll(addDaySelectionRange());
        ChoiceBox<String> cbMonth = new ChoiceBox<>();
        cbMonth.setValue("january");
        cbMonth.getItems().addAll(new String[]{"january", "february", "march", "april", "may", "june", "july",
            "august", "september", "october", "november", "december"});
        TextField tfYear = new TextField();

        //bills list feature
        Label lbListBills = new Label("Bills list");
        TextArea taListBills = new TextArea();
        VBox vListBills = new VBox(5);
        
        //actions appending
        hAddContainer.getChildren().addAll(vAddBill, vAddEmployee);
        vActionContainer.getChildren().add(hAddContainer);

        //Cheques
        VBox vCheques = new VBox(5);
        vCheques.setPrefWidth(500);
        Button btnIssueCheques = new Button("Issue Cheques");
        btnIssueCheques.setPrefWidth(550);
        TextArea taListCheques = new TextArea();
        vCheques.getChildren().addAll(taListCheques, btnIssueCheques);
        vActionContainer.getChildren().addAll(vCheques);

        //exit feature
        Button btnExit = new Button("EXIT");
        btnExit.setPrefWidth(500);

        //general appending
        root.getChildren().addAll(hMainContainer);
        hMainContainer.getChildren().addAll(vActionContainer, vListContainer);

        //lists appending
        vListBills.getChildren().addAll(lbListBills, taListBills);
        vListEmployees.getChildren().addAll(lbListEmployees, taListEmployees);
        vListContainer.getChildren().addAll(vListEmployees, vListBills, btnExit);
        
        //appending bills
        vAddBill.getChildren().addAll(lbCompanyName, tfCompanyName, lbAmountToPay, tfAmountToPay, lbDueDate, hSelectDate, btnAddBill);
        hSelectDate.getChildren().addAll(cbDay, cbMonth, tfYear);

        //appending employees
        vAddEmployee.getChildren().addAll(lbFirstName, tfFirstName, lbLastName, tfLastName, lbAge, tfAge, radioFullTime, radioPartTime, vSalary, btnAddEmployee);

        // scene is associated with container, dimensions
        Scene scene = new Scene(root, 1050, 600);

        // associate scene to stage and show
        stage.setTitle("Company management software");
        stage.setScene(scene);
        stage.show();

        //calling event handlers
        availabilities.selectedToggleProperty().addListener(
            (observable, oldToggle, newToggle) -> {
                update_availabilities(newToggle, radioPartTime, radioFullTime, vSalary);
            }
        );

        this.company.load_employees_from_file();
        update_availabilities(radioFullTime, radioPartTime, radioFullTime, vSalary);
        taListEmployees.setText(this.company.show_all_employees());
        taListBills.setText(this.company.show_all_bills());
        add_bill(taListBills, btnAddBill, tfCompanyName, tfAmountToPay, tfYear, cbDay, cbMonth);
        add_employee(btnAddEmployee, taListEmployees, vAddEmployee);
        issue_cheques(btnIssueCheques, taListCheques);
        show_cheques(taListCheques);
        exitApp(btnExit);
    }

    /**
     * This method dynamically generates an array of number from 1 to 31
     * representing the number of days in a month
     * @return Integer[]
     */
    public Integer[] addDaySelectionRange() {
        Integer[] days = new Integer[31];
        for (int i = 0; i < days.length; i++) {
            days[i] = i+1;
        }
        return days;
    }

    /**
     * this method creates an event handler for the exit button
     * every time the button is clicked, the application will be closed
     * @param btnExit
     */
    public void exitApp(Button btnExit) {
        btnExit.setOnAction(evt -> {
            Platform.exit();
        });
    }

    /**
     * this method creates an event handler for the issue cheques button
     * every time the button is clicked, new cheques will be issued and
     * displayed for each payable (bills and employees)
     * @param btnIssueCheques
     * @param taListAllCheques
     */
    public void issue_cheques(Button btnIssueCheques, TextArea taListAllCheques) {
        btnIssueCheques.setOnAction(evt -> {
            this.company.issue_cheques();
            show_cheques(taListAllCheques);
        });
    }

    /**
     * This method show all the cheques that have been issued
     * This is made to update everytime new cheques are issued
     * @param taListAllCheques
     */
    public void show_cheques(TextArea taListAllCheques) {
        taListAllCheques.setText(this.company.show_all_cheques());
    }

    /**
     * this method updates the input options in the availability selection
     * it creates different input option depending on whether the user has
     * selected full-time or part-time, data displayed and treated will be different
     * @param selected_availability
     * @param partTime
     * @param fullTime
     * @param vsalary
     */
    public void update_availabilities(Toggle selected_availability, ToggleButton partTime, ToggleButton fullTime, VBox vsalary) {
        if(selected_availability.equals(fullTime)) {
            vsalary.getChildren().clear();
            Label lbMonthlySalary = new Label("Monthly salary : ");
            TextField tfMonthlySalary = new TextField();
            vsalary.getChildren().addAll(lbMonthlySalary, tfMonthlySalary);
        } else if(selected_availability.equals(partTime)) {
            vsalary.getChildren().clear();
            Label lbHoursWorked = new Label("nb. Hours worked : ");
            TextField tfHoursWorked = new TextField();
            Label lbEchelon = new Label("Echelon : ");
            ChoiceBox<Integer> cbEchelon = new ChoiceBox<>();
            cbEchelon.getItems().addAll(1, 2, 3, 4, 5);
            vsalary.getChildren().addAll(lbHoursWorked, tfHoursWorked, lbEchelon, cbEchelon);
        } else {
            throw new IllegalArgumentException("This error cannot occur from the user side, check the code for the radio buttons");
        }
    }

    /**
     * This method sets an action listener on the add bill button
     * every time this button is clicked, a new bill will be added 
     * according to the values entered in the textfields by the user
     * @param taListBills
     * @param btnAddBill
     * @param tfCompanyName
     * @param tfAmountToPay
     * @param tfYear
     * @param cbDay
     * @param cbMonth
     */
    public void add_bill(TextArea taListBills, Button btnAddBill, TextField tfCompanyName, TextField tfAmountToPay, TextField tfYear, ChoiceBox<Integer> cbDay, ChoiceBox<String> cbMonth) {
        btnAddBill.setOnAction(evt -> {
            try {
                Date date = new Date(cbMonth.getValue().toString(), Integer.parseInt(cbDay.getValue().toString()), Integer.parseInt(tfYear.getText()));
                this.company.add_bill(tfCompanyName.getText(), Double.parseDouble(tfAmountToPay.getText()), date);
                taListBills.setText(this.company.show_all_bills());
            } catch (NumberFormatException e) {
                System.out.println("Input format for bill is invalid, the user should check the information");
            }
        });
    }

    /**
     * This method sets an action listener on the add employee button
     * every time this button is clicked, a new employee will be added 
     * according to the values entered in the textfields by the user
     * @param btnAddEmployee
     * @param taListEmployees
     * @param vEmployeeInfos
     */
    public void add_employee(Button btnAddEmployee, TextArea taListEmployees, VBox vEmployeeInfos) {
        btnAddEmployee.setOnAction(evt -> {
            try {
                VBox vsalaryInfo = (VBox) vEmployeeInfos.getChildren().get(8);
                TextField first_name = (TextField) vEmployeeInfos.getChildren().get(1);
                TextField last_name = (TextField) vEmployeeInfos.getChildren().get(3);
                TextField age = (TextField) vEmployeeInfos.getChildren().get(5);
                
                if(vsalaryInfo.getChildren().size() == 2) {
                    TextField tfMonthlySalary = (TextField) vsalaryInfo.getChildren().get(1);
                    this.company.add_employee(Double.parseDouble(tfMonthlySalary.getText()), first_name.getText(), last_name.getText(), Integer.parseInt(age.getText()));
                } else if(vsalaryInfo.getChildren().size() == 4) {
                    ChoiceBox<Integer> cbechelon = (ChoiceBox<Integer>) vsalaryInfo.getChildren().get(3);
                    int echelon = Integer.parseInt(cbechelon.getSelectionModel().getSelectedItem().toString());
                    TextField hours_worked = (TextField) vsalaryInfo.getChildren().get(1);
                    this.company.add_employee(echelon, Double.parseDouble(hours_worked.getText()), first_name.getText(), last_name.getText(), Integer.parseInt(age.getText()));
                } else {
                    throw new IllegalArgumentException("This error cannot occur on the user side, please check the code for the add_employee method");
                }
                // the line below updates the employees list
                taListEmployees.setText(this.company.show_all_employees());
            } catch (Exception e) {
                System.out.println("An error occured while trying to add the employee");
                e.printStackTrace();
            }
        });
    }
}