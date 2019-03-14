package nk.parking.fees;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee,Long> {
}
