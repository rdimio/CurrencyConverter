package vadimio.ru.CurrencyConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Short id;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "num_code")
    private Short numCode;

    @Column(name = "char_code")
    private String charCode;

    @Column
    private Short nominal;

    @Column
    private String name;

    @Column
    private Float value;

    @Column
    private Date date;

    public Quote(String currencyId, Short numCode, String charCode, Short nominal, String name, Float value, Date date) {
        this.currencyId = currencyId;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.date = date;
    }
}
