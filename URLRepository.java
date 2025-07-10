import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface URLRepository extends JpaRepository<URLMapping, Long> {
Optional<URLMapping> findByShortCode(String shortCode);
}