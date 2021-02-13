package github.JulianNSH.SHOP.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Products {
    @GetMapping("/products")
    public String showProducts(){
        return "products";
    }
}
