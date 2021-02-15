package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class ProductGroups {
    @GetMapping("/product_groups")
public String showProductGroups(Model model) throws SQLException {
        StringBuilder pgData = new StringBuilder();
        String pgQuery = "SELECT * FROM product_groups";
        Statement pgStatement = DatabaseConnector.connect().createStatement();
        ResultSet pgResultSet = pgStatement.executeQuery(pgQuery);

        while (pgResultSet.next()){
            pgData.append("<tr><td>").append(pgResultSet.getInt(1)).append("<td>")
                    .append(pgResultSet.getString(2)).append("</td>")
                    .append("<td>").append(pgResultSet.getInt(3)).append("</td>").append("</td></tr>");
        }

        model.addAttribute("productGroupsData", pgData);
        return "productGroups";
    }
}
