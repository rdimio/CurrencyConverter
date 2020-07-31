package vadimio.ru.CurrencyConverter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomDate {
    public static String customDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        return date;
    }
}
