package vadimio.ru.CurrencyConverter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadimio.ru.CurrencyConverter.entities.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Short> {
    Quote findOneByValuteId(String valuteId);
}
