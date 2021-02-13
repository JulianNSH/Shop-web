package github.JulianNSH.SHOP.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sales {
    @GetMapping("/sales")
    public String showSales(){
        return "sales";
    }
}
