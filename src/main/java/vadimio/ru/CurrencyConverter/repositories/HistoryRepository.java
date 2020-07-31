package vadimio.ru.CurrencyConverter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadimio.ru.CurrencyConverter.entities.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Short> {
}
