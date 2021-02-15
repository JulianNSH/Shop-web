package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@Controller
public class Employees {

    @GetMapping("/employees")
    public String showEmployees(Model model) throws SQLException {
        //SB object for holding query final result
        StringBuilder employeesData = new StringBuilder();
        String employeeQuery = "SELECT * FROM employees";

        Statement employeeStatement = DatabaseConnector.connect().createStatement();
        //get the result of faculties query to object
        ResultSet employeeResultSet = employeeStatement.executeQuery(employeeQuery);
        //combine result and HTML table structure
        while (employeeResultSet.next()){
            employeesData.append("<tr><td>").append(employeeResultSet.getInt(1)).append("<td>")
                    .append(employeeResultSet.getString(2)).append("</td>")
                    .append("<td>").append(employeeResultSet.getString(3)).append("</td>")
                    .append("<td>").append(employeeResultSet.getString(4)).append("</td>")
                    .append("<td>").append(employeeResultSet.getInt(5)).append("</td>")
                    .append("<td>").append(employeeResultSet.getDouble(6)).append("</td></tr>");
        }

        model.addAttribute("employeesData", employeesData);
        model.addAttribute("employeeForm", new EmployeesClass());
        return "employees";
    }

    @PostMapping("/addFormEmployees")
    public String addEmployee(@ModelAttribute("employeeForm") EmployeesClass employee, BindingResult bindingResult,
                              Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "employees";
        }

        if(employee.id==0 || employee.name==null || employee.surname==null ||  employee.position==null ||
                    employee.age==0 || employee.salary==0){

                String message="Empty data. Complete all fields";
                model.addAttribute("message", message);
            } else {
                //insert data to Database
                Connection connector = DatabaseConnector.connect();
                if (connector != null) {
                    String sqlQuery = "INSERT INTO employees(id_employee, name_empl, surname_empl, position, age, salary) VALUES(?,?,?,?,?,?)";
                    PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                    sqlStatement.setInt(1, employee.id);
                    sqlStatement.setString(2, employee.name);
                    sqlStatement.setString(3, employee.surname);
                    sqlStatement.setString(4, employee.position);
                    sqlStatement.setInt(5, employee.age);
                    sqlStatement.setDouble(6, employee.salary);

                    sqlStatement.executeUpdate();
                    sqlStatement.close();
                   // System.out.println("Row added successfully");
                }
            }
        return "redirect:/employees";
    }
    @PostMapping("/updateFormEmployees")
    public String updateEmployee(@ModelAttribute("employeeForm") EmployeesClass employee, BindingResult bindingResult,
                              Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "employees";
        }

        if(employee.id==0 || employee.name==null || employee.surname==null ||  employee.position==null ||
                employee.age==0 || employee.salary==0){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //update data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){
                String sqlQuery = "UPDATE employees SET name_empl= ?, surname_empl= ?, position= ?, age = ?, salary = ?  WHERE id_employee = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setString(1, employee.name);
                sqlStatement.setString(2, employee.surname);
                sqlStatement.setString(3, employee.position);
                sqlStatement.setInt(4, employee.age);
                sqlStatement.setDouble(5, employee.salary);
                sqlStatement.setInt(6, employee.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                //System.out.println("Row Updated successfully");
            }
        }
        return "redirect:/employees";
    }

    @PostMapping("/deleteFormEmployees")
    public String deleteEmployee(@ModelAttribute("employeeForm") EmployeesClass employee, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "employees";
        }

        if(employee.id==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //delete data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){

                String sqlQuery = "DELETE FROM employees WHERE id_employee = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, employee.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();

                //System.out.println("Row deleted successfully");
            }
        }
        return "redirect:/employees";
    }
}
