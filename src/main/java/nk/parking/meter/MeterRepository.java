package nk.parking.meter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterRepository extends JpaRepository<Meter,Long> {
}
