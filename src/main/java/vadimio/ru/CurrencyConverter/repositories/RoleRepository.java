package vadimio.ru.CurrencyConverter.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadimio.ru.CurrencyConverter.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    <T> T findOneByName(String role_user);
}
