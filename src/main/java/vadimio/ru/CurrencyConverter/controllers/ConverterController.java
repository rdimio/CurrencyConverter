package vadimio.ru.CurrencyConverter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vadimio.ru.CurrencyConverter.dto.FilterDto;
import vadimio.ru.CurrencyConverter.dto.HistoryDto;
import vadimio.ru.CurrencyConverter.entities.History;
import vadimio.ru.CurrencyConverter.entities.Quote;
import vadimio.ru.CurrencyConverter.entities.User;
import vadimio.ru.CurrencyConverter.services.HistoryService;
import vadimio.ru.CurrencyConverter.services.QuoteService;
import vadimio.ru.CurrencyConverter.services.UserService;
import vadimio.ru.CurrencyConverter.utils.CustomDate;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        initializeQuotes(model);
        return "converter";
    }

    @GetMapping("/history")
    public String shoeHistory(Model model, Principal principal){
        String name = principal.getName();
        User user = userService.findByLogin(name);
        List<History> list = user.getHistories();
        FilterDto fdto = new FilterDto();
        model.addAttribute("list", list);
        model.addAttribute("filterDto", fdto);
        initializeQuotes(model);
        return "history";
    }

    @PostMapping("/history")
    public String saveConversion(@ModelAttribute(name = "historyDto") HistoryDto hdto,
                                 Principal principal,
                                 Model model) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");

        String name = principal.getName();
        User user = userService.findByLogin(name);

        Quote src = quoteService.findOneByCharCode(hdto.getSrcCurrency());
        Quote dst = quoteService.findOneByCharCode(hdto.getDstCurrency());

        DecimalFormat df = new DecimalFormat("###.##");
        float result = Float.parseFloat(df.format(hdto.getSrcSum() * dst.getValue() / src.getValue()).replace(',','.'));

        try {
            History history = new History(
                    (src.getCharCode() + '(' + src.getName() + ')'),
                    (dst.getCharCode() + '(' + dst.getName() + ')'),
                    hdto.getSrcSum(),
                    result,
                    format.parse(CustomDate.customDate()),
                    user
            );
            historyService.save(history);
        } catch (ParseException e) {
        }
        model.addAttribute("srcCharCode", src.getCharCode() + '(' + src.getName() + ')');
        model.addAttribute("dstCharCode", dst.getCharCode() + '(' + dst.getName() + ')');
        model.addAttribute("result", result);
        model.addAttribute("src", hdto.getSrcSum());
        initializeQuotes(model);
        return "converter";
    }

    @PostMapping("/history/search")
        public String applyFilters(@ModelAttribute(name = "filterDto") FilterDto fdto,
                Principal principal,
                Model model){
        String name = principal.getName();
        User user = userService.findByLogin(name);
        List<History> list = historyService.applyFilters(fdto.getSrcCurrency(), fdto.getDstCurrency(), user);
        model.addAttribute("list", list);
        initializeQuotes(model);
        return "history";
    }

    private void initializeQuotes(Model model){
        List<Quote> quotes;
        quotes = quoteService.findAll();
        HistoryDto hdto = new HistoryDto();
        model.addAttribute("quotes", quotes);
        model.addAttribute("historyDto", hdto);
    }
}


