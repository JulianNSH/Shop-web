package github.JulianNSH.SHOP.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Buyers {
    @GetMapping("/buyers")
    public String showBuyers(){
        return "buyers";
    }
}
