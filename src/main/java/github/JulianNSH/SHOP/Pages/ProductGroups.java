package github.JulianNSH.SHOP.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductGroups {
    @GetMapping("/product_groups")
    public String showProductGroups(){
        return "productGroups";
    }
}
