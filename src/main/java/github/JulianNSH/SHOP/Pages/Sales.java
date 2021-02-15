package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;

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
        model.addAttribute( "salesForm", new SalesClass());
        return "sales";
    }

    @PostMapping("/addFormSales")
    public String addSales(@ModelAttribute("salesForm") SalesClass sales, BindingResult bindingResult,
                              Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "sales";
        }

        if(sales.id==0 || sales.idProduct==0 || sales.discount==0){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //insert data to Database
            Connection connector = DatabaseConnector.connect();
            if (connector != null) {
                String sqlQuery = "INSERT INTO sales(id_sale, id_product, discount) VALUES(?,?,?)";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, sales.id);
                sqlStatement.setInt(2, sales.idProduct);
                sqlStatement.setInt(3, sales.discount);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                // System.out.println("Row added successfully");
            }
        }
        return "redirect:/sales";
    }
    @PostMapping("/updateFormSales")
    public String updateSales(@ModelAttribute("salesForm") SalesClass sales, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "sales";
        }

        if(sales.id==0 || sales.idProduct==0 || sales.discount==0){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //update data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){
                String sqlQuery = "UPDATE sales SET id_product = ?, discount =? WHERE id_sale = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, sales.idProduct);
                sqlStatement.setInt(2, sales.discount);
                sqlStatement.setInt(3, sales.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                //System.out.println("Row Updated successfully");
            }
        }
        return "redirect:/sales";
    }

    @PostMapping("/deleteFormSales")
    public String deleteSales(@ModelAttribute("salesForm") SalesClass sales, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "sales";
        }

        if(sales.id==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //delete data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){

                String sqlQuery = "DELETE FROM sales WHERE id_sale = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, sales.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();

                //System.out.println("Row deleted successfully");
            }
        }
        return "redirect:/sales";
    }
}
