package vadimio.ru.CurrencyConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Short id;

    @Column(name = "src_currency")
    private String srcCurrency;

    @Column(name = "dst_currency")
    private String dstCurrency;

    @Column(name = "src_sum")
    private Integer srcSum;

    @Column(name = "dst_sum")
    private Float dstSum;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public History(String srcCurrency, String dstCurrency, Integer srcSum, Float dstSum, Date date, User user) {
        this.srcCurrency = srcCurrency;
        this.dstCurrency = dstCurrency;
        this.srcSum = srcSum;
        this.dstSum = dstSum;
        this.date = date;
        this.user = user;
    }
}
