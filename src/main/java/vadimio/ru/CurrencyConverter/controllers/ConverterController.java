package vadimio.ru.CurrencyConverter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vadimio.ru.CurrencyConverter.services.QuoteService;

@Controller
public class ConverterController {

    private QuoteService quoteService;

    @Autowired
    public void setQuoteService(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    @GetMapping("/")
    public String start(){
        quoteService.loadQuotes();
        return "redirect:/convert/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }
}
