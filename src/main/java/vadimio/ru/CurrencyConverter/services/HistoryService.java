package vadimio.ru.CurrencyConverter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vadimio.ru.CurrencyConverter.entities.History;
import vadimio.ru.CurrencyConverter.repositories.HistoryRepository;

@Service
public class HistoryService {

    private HistoryRepository historyRepository;

    @Autowired
    public void setHistoryRepository(HistoryRepository historyRepository){
        this.historyRepository = historyRepository;
    }

    public void save(History history) {
        historyRepository.save(history);
    }
}
