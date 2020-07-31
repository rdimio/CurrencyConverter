package vadimio.ru.CurrencyConverter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoryDto {
    private String srcCurrency;
    private String dstCurrency;
    private Integer srcSum;
}
