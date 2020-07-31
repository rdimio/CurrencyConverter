package vadimio.ru.CurrencyConverter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterDto {
    private String srcCurrency;
    private String dstCurrency;
}
