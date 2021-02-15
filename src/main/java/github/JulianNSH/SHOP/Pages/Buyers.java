package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class Buyers {
    @GetMapping("/buyers")
    public String showBuyers(Model model) throws SQLException {

        StringBuilder buyersData = new StringBuilder();
        String buyersQuery = "SELECT * FROM buyerscc";
        Statement buyersStatement = DatabaseConnector.connect().createStatement();
        ResultSet buyersResultSet = buyersStatement.executeQuery(buyersQuery);

        while (buyersResultSet.next()){
            buyersData.append("<tr><td>").append(buyersResultSet.getInt(1)).append("<td>")
                    .append(buyersResultSet.getString(2)).append("</td>")
                    .append("<td>").append(buyersResultSet.getString(3)).append("</td>")
                    .append("<td>").append(buyersResultSet.getDouble(4)).append("</td>")
                    .append("<td>").append(buyersResultSet.getInt(5)).append("</td></tr>");
        }

        model.addAttribute("buyersData", buyersData);
        return "buyers";
    }
}
