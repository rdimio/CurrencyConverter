package vadimio.ru.CurrencyConverter.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vadimio.ru.CurrencyConverter.entities.Quote;
import vadimio.ru.CurrencyConverter.repositories.QuoteRepository;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class QuoteService {

    private QuoteRepository quoteRepository;

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }


    public void loadQuotes() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            Document doc = Jsoup.connect("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date).get();
            Elements valuteId = doc.getElementsByAttribute("ID");
            for (Element e : valuteId) {
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd/MM/yyyy");
                Quote quote = new Quote(
                        e.attr("ID"),
                        Short.parseShort(e.getElementsByTag("NumCode").text()),
                        e.getElementsByTag("CharCode").text(),
                        Short.parseShort(e.getElementsByTag("Nominal").text()),
                        e.getElementsByTag("Name").text(),
                        Float.parseFloat(e.getElementsByTag("Value").text().replace(',','.')),
                        format.parse(date));
                Quote existQuote = quoteRepository.findOneByValuteId(quote.getValuteId());
                if(existQuote == null) quoteRepository.save(quote);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
