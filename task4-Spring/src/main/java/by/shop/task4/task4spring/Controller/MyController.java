package by.shop.task4.task4spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/hello")
    public String sayHello(Model model) {
        System.out.println("!!! МЕТОД SayHello БЫЛ ВЫЗВАН !!!");
        model.addAttribute("message", "Привет, мир!");
        return "sec";
    }
}