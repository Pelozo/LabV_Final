package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
