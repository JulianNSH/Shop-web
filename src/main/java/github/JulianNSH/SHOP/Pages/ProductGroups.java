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
        model.addAttribute("productGroupsForm", new ProductGroupsClass());
        return "product_groups";
    }

    @PostMapping("/addFormPG")
    public String addProductGroup(@ModelAttribute("productGroupsForm") ProductGroupsClass productGroups, BindingResult bindingResult,
                             Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "product_groups";
        }

        if(productGroups.id==0 || productGroups.groupName==null || productGroups.nrOfUnits==0 ){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //insert data to Database
            Connection connector = DatabaseConnector.connect();
            if (connector != null) {
                String sqlQuery = "INSERT INTO product_groups(id_group, group_name, units_number) VALUES(?,?,?)";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, productGroups.id);
                sqlStatement.setString(2, productGroups.groupName);
                sqlStatement.setInt(3, productGroups.nrOfUnits);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                // System.out.println("Row added successfully");
            }
        }
        return "redirect:/product_groups";
    }
    @PostMapping("/updateFormPG")
    public String updateProductGroup(@ModelAttribute("productGroupsForm") ProductGroupsClass productGroups, BindingResult bindingResult,
                                Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "product_groups";
        }

        if(productGroups.id==0 || productGroups.groupName==null ||  productGroups.nrOfUnits==0 ){

            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //update data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){
                String sqlQuery = "UPDATE product_groups SET group_name = ?, units_number =? WHERE id_group = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setString(1, productGroups.groupName);
                sqlStatement.setInt(2, productGroups.nrOfUnits);
                sqlStatement.setInt(3, productGroups.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                //System.out.println("Row Updated successfully");
            }
        }
        return "redirect:/product_groups";
    }

    @PostMapping("/deleteFormPG")
    public String deleteProductGroup(@ModelAttribute("productGroupsForm") ProductGroupsClass productGroup, BindingResult bindingResult,
                                Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "product_group";
        }

        if(productGroup.id==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //delete data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){

                String sqlQuery = "DELETE FROM product_groups WHERE id_group = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, productGroup.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();

                //System.out.println("Row deleted successfully");
            }
        }
        return "redirect:/product_groups";
    }
}
