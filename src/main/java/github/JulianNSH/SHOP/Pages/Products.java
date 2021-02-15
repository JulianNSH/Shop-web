package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class Products {
    @GetMapping("/products")
    public String showProducts(Model model) throws SQLException {
        StringBuilder productsData = new StringBuilder();
        String productsQuery = "SELECT * from products JOIN product_groups pg on products.id_group = pg.id_group";
        Statement productsStatement = DatabaseConnector.connect().createStatement();
        ResultSet productsResultSet = productsStatement.executeQuery(productsQuery);
        while (productsResultSet.next()){
            productsData.append("<tr><td>").append(productsResultSet.getInt("id_product")).append("<td>")
                    .append(productsResultSet.getString("name_product")).append("</td>")
                    .append("<td>").append(productsResultSet.getString("group_name")).append("</td>")
                    .append("<td>").append(productsResultSet.getDouble("price")).append("</td></tr>");
        }

        model.addAttribute("productsData", productsData);
        return "products";
    }
}
