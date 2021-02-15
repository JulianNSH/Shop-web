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
        model.addAttribute("productsForm", new ProductsClass());
        return "products";
    }
    @PostMapping("/addFormProducts")
    public String addProduct(@ModelAttribute("productsForm") ProductsClass products, BindingResult bindingResult,
                              Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "products";
        }

        if(products.id==0 || products.name==null || products.groupId==0 || products.price==0){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //insert data to Database
            Connection connector = DatabaseConnector.connect();
            if (connector != null) {
                String sqlQuery = "INSERT INTO products(id_product, name_product, id_group, price) VALUES(?,?,?,?)";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, products.id);
                sqlStatement.setString(2, products.name);
                sqlStatement.setInt(3, products.groupId);
                sqlStatement.setDouble(4, products.price);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                // System.out.println("Row added successfully");
            }
        }
        return "redirect:/products";
    }
    @PostMapping("/updateFormProducts")
    public String updateProduct(@ModelAttribute("productsForm") ProductsClass products, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "products";
        }

        if(products.id==0 || products.name==null ||  products.groupId==0 || products.price==0){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //update data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){
                String sqlQuery = "UPDATE products SET name_product = ?, id_group = ?, price = ? WHERE id_product = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setString(1, products.name);
                sqlStatement.setInt(2, products.groupId);
                sqlStatement.setDouble(3, products.price);
                sqlStatement.setInt(4, products.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                //System.out.println("Row Updated successfully");
            }
        }
        return "redirect:/products";
    }

    @PostMapping("/deleteFormProducts")
    public String deleteProduct(@ModelAttribute("productsForm") ProductsClass products, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "products";
        }

        if(products.id==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //delete data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){

                String sqlQuery = "DELETE FROM products WHERE id_product = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, products.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();

                //System.out.println("Row deleted successfully");
            }
        }
        return "redirect:/products";
    }
}
