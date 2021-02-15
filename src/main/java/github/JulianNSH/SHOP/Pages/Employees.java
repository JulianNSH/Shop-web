package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        return "employees";
    }
}
