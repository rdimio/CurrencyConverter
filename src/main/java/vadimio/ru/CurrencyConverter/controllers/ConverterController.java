package vadimio.ru.CurrencyConverter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vadimio.ru.CurrencyConverter.dto.HistoryDto;
import vadimio.ru.CurrencyConverter.entities.History;
import vadimio.ru.CurrencyConverter.entities.Quote;
import vadimio.ru.CurrencyConverter.entities.User;
import vadimio.ru.CurrencyConverter.services.HistoryService;
import vadimio.ru.CurrencyConverter.services.QuoteService;
import vadimio.ru.CurrencyConverter.services.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class ConverterController {

    private QuoteService quoteService;
    private UserService userService;
    private HistoryService historyService;

    @Autowired
    public void setQuoteService(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String start() {
        quoteService.loadQuotes();
        return "redirect:/convert/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/convert")
    public String convert(Model model) {
        quoteService.loadQuotes();
        List<Quote> quotes;
        quotes = quoteService.findAll();
        HistoryDto hdto = new HistoryDto();
        model.addAttribute("quotes", quotes);
        model.addAttribute("historyDto", hdto);
        return "converter";
    }

    @PostMapping("/history")
    public String saveConversion(@ModelAttribute(name = "historyDto") HistoryDto hdto,
                                 Principal principal,
                                 Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        String name = principal.getName();
        User user = userService.findByLogin(name);

        Quote src = quoteService.findOneByCharCode(hdto.getSrcCurrency());
        Quote dst = quoteService.findOneByCharCode(hdto.getDstCurrency());

        float result = hdto.getSrcSum() * dst.getValue() / src.getValue();

        try {
            History history = new History(
                    (src.getCharCode() + '(' + src.getName() + ')'),
                    (dst.getCharCode() + '(' + dst.getName() + ')'),
                    hdto.getSrcSum(),
                    result,
                    dateFormat.parse(date),
                    user
            );
            historyService.save(history);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("result", result);
        return convert(model);
    }
}
