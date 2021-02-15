package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class Sales {
    @GetMapping("/sales")
    public String showSales(Model model) throws SQLException {

        StringBuilder salesData = new StringBuilder();
        String salesQuery = "SELECT * from sales JOIN products p on p.id_product = sales.id_product";
        Statement salesStatement = DatabaseConnector.connect().createStatement();
        ResultSet salesResultSet = salesStatement.executeQuery(salesQuery);

        while (salesResultSet.next()){
            salesData.append("<tr><td>").append(salesResultSet.getInt("id_sale")).append("<td>")
                    .append(salesResultSet.getString("name_product")).append("</td>")
                    .append("<td>").append(salesResultSet.getInt("discount")).append("</td></tr>");
        }

        model.addAttribute("salesData", salesData);
        return "sales";
    }
}
