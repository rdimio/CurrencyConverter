package vadimio.ru.CurrencyConverter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vadimio.ru.CurrencyConverter.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByLogin(String login);

}