package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {

    //TODO preguntar al profe porque no encuentra el id
//    @Query(value = "SELECT M.kwh_value " +
//            "FROM MEASUREMENTS M " +
//            "JOIN RESIDENCES R " +
//            "ON M.RESIDENCE_ID = R.ID " +
//            "WHERE R.CLIENT_ID = :clientId AND M.DATE BETWEEN :from AND :to "
//            ,nativeQuery = true)
//    Page<Measurement> findMeasurementsByRangeOfDates(@Param("clientId")long id, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);
}
