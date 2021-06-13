package net.pelozo.FinalTPLab5DB2.model.TestDeleteLater;

import net.pelozo.FinalTPLab5DB2.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterModelRepository extends JpaRepository<MeterModel2, Long> {
}
