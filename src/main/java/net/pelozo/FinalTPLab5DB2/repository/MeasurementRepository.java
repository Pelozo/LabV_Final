package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Measurement;
import net.pelozo.FinalTPLab5DB2.projections.MeasurementProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {

    //TODO preguntar al profe porque no encuentra el id
    @Query(value = "SELECT M.*" +
            "FROM MEASUREMENTS M " +
            "JOIN RESIDENCES R " +
            "ON M.RESIDENCE_ID = R.ID " +
            "WHERE R.CLIENT_ID = :clientId AND M.DATE BETWEEN :from AND :to "
            ,nativeQuery = true)
    LinkedList<Measurement> findListOfIntakeByRangeOfDates(@Param("clientId")long clientId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);


    @Query(value = "SELECT M.kwh_price AS `kwhPrice`, M.kwh_value AS `kwhValue`" +
            "FROM MEASUREMENTS M " +
            "JOIN RESIDENCES R " +
            "ON M.RESIDENCE_ID = R.ID " +
            "WHERE R.CLIENT_ID = :clientId AND M.DATE BETWEEN :from AND :to "
            ,nativeQuery = true)
    Page<MeasurementProjection> findMeasurementsByRangeOfDate(@Param("clientId")long clientId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);
}
