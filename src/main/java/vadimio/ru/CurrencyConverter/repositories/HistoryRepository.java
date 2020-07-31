package vadimio.ru.CurrencyConverter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vadimio.ru.CurrencyConverter.entities.History;
import vadimio.ru.CurrencyConverter.entities.User;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Short> {
    @Query("SELECT h FROM History h WHERE h.srcCurrency = :src AND h.dstCurrency = :dst AND h.user = :user")
    List<History> applyFilters(String src, String dst, User user);
}
