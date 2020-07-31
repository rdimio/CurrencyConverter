package vadimio.ru.CurrencyConverter.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vadimio.ru.CurrencyConverter.entities.Quote;
import vadimio.ru.CurrencyConverter.repositories.QuoteRepository;
import vadimio.ru.CurrencyConverter.utils.CustomDate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class QuoteService {

    private QuoteRepository quoteRepository;

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Value("${centro.bank.address}")
    private String centroBankAdress;

    public void loadQuotes() {
        try {
            String date = CustomDate.customDate();
            Document doc = Jsoup.connect(centroBankAdress + date).get();
            Elements valuteId = doc.getElementsByAttribute("ID");
            for (Element e : valuteId) {
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd.MM.yyyy");
                Quote quote = new Quote(
                        e.attr("ID"),
                        Short.parseShort(e.getElementsByTag("NumCode").text()),
                        e.getElementsByTag("CharCode").text(),
                        Short.parseShort(e.getElementsByTag("Nominal").text()),
                        e.getElementsByTag("Name").text(),
                        Float.parseFloat(e.getElementsByTag("Value").text().replace(',','.')),
                        format.parse(date));
                Quote existQuote = quoteRepository.findOneByCurrencyId(quote.getCurrencyId());
                if(existQuote != null) quoteRepository.delete(existQuote);
                quoteRepository.save(quote);
            }
        } catch (IOException | ParseException e) {
        }
    }

    public List<Quote> findAll() {
        return quoteRepository.findAll();
    }

    public Quote findOneByCharCode(String code) {
        return quoteRepository.findOneByCharCode(code);
    }
}
