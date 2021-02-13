package github.JulianNSH.SHOP.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Employees {
    @GetMapping("/employees")
    public String showEmployees(){
        return "employees";
    }
}