package vadimio.ru.CurrencyConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Short id;

    @Column(name = "valute_id")
    private String valuteId;

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

    @ManyToMany
    @JoinTable(name = "users_quotes",
            joinColumns = @JoinColumn(name = "quote_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Quote(String valuteId, Short numCode, String charCode, Short nominal, String name, Float value, Date date) {
        this.valuteId = valuteId;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.date = date;
    }
}
