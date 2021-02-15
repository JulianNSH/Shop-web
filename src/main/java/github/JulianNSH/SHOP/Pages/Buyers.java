package github.JulianNSH.SHOP.Pages;

import github.JulianNSH.SHOP.config.DatabaseConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;
/*
Controller of buyers entity
 */
@Controller
public class Buyers {
    @GetMapping("/buyers")
    public String showBuyers(Model model) throws SQLException {
        //SB to show in html page
        StringBuilder buyersData = new StringBuilder();
        //Query, Statement, Result set to get data from DB
        String buyersQuery = "SELECT * FROM buyerscc";
        Statement buyersStatement = DatabaseConnector.connect().createStatement();
        ResultSet buyersResultSet = buyersStatement.executeQuery(buyersQuery);

        //insert data into SB from RS
        while (buyersResultSet.next()){
            buyersData.append("<tr><td>").append(buyersResultSet.getInt(1)).append("<td>")
                    .append(buyersResultSet.getString(2)).append("</td>")
                    .append("<td>").append(buyersResultSet.getString(3)).append("</td>")
                    .append("<td>").append(buyersResultSet.getDouble(4)).append("</td>")
                    .append("<td>").append(buyersResultSet.getInt(5)).append("</td></tr>");
        }

        //Add SB to model and initialize field for forms
        model.addAttribute("buyersData", buyersData);
        model.addAttribute("buyersForm", new BuyersClass());
        return "buyers";
    }

    //method to handle Add form
    @PostMapping("/addFormBuyers")
    public String addBuyers(@ModelAttribute("buyersForm") BuyersClass buyer, BindingResult bindingResult,
                              Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "buyers";
        }

        if(buyer.id==0 || buyer.name==null || buyer.surname==null ||  buyer.acquisitions==0 || buyer.discount==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //insert data to Database
            Connection connector = DatabaseConnector.connect();
            if (connector != null) {
                String sqlQuery = "INSERT INTO buyerscc(id_buyer, name_buyer, surname_buyer, acquisitions, discount) VALUES(?,?,?,?,?)";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, buyer.id);
                sqlStatement.setString(2, buyer.name);
                sqlStatement.setString(3, buyer.surname);
                sqlStatement.setDouble(4, buyer.acquisitions);
                sqlStatement.setInt(5, buyer.discount);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                // System.out.println("Row added successfully");
            }
        }
        return "redirect:/buyers";
    }

    //method to handle Update form
    @PostMapping("/updateFormBuyers")
    public String updateBuyers(@ModelAttribute("buyersForm") BuyersClass buyer, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "buyers";
        }


        if(buyer.id==0 || buyer.name==null || buyer.surname==null ||  buyer.acquisitions==0 || buyer.discount==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //update data to Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){
                String sqlQuery = "UPDATE buyerscc SET name_buyer= ?, surname_buyer= ?, acquisitions= ?, discount= ? WHERE id_buyer = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setString(1, buyer.name);
                sqlStatement.setString(2, buyer.surname);
                sqlStatement.setDouble(3, buyer.acquisitions);
                sqlStatement.setInt(4, buyer.discount);
                sqlStatement.setInt(5, buyer.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();
                //System.out.println("Row Updated successfully");
            }
        }
        return "redirect:/buyers";
    }

    //method to handle Delete form
    @PostMapping("/deleteFormBuyers")
    public String deleteBuyers(@ModelAttribute("buyersForm") BuyersClass buyer, BindingResult bindingResult,
                                 Model model) throws SQLException{
        if (bindingResult.hasErrors()) {
            System.out.println("There was a error "+bindingResult);
            return "buyers";
        }

        if(buyer.id==0){
            String message="Empty data. Complete all fields";
            model.addAttribute("message", message);
        } else {
            //delete data from Database
            Connection connector = DatabaseConnector.connect();
            if(connector!=null){

                String sqlQuery = "DELETE FROM buyerscc WHERE id_buyer = ?";
                PreparedStatement sqlStatement = connector.prepareStatement(sqlQuery);

                sqlStatement.setInt(1, buyer.id);

                sqlStatement.executeUpdate();
                sqlStatement.close();

                //System.out.println("Row deleted successfully");
            }
        }
        return "redirect:/buyers";
    }
}
