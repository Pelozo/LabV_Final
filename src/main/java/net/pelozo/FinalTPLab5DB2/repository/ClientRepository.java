package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT c.* FROM measurements m\n" +
            "JOIN residences r\n" +
            "ON r.id = m.residence_id\n" +
            "JOIN clients c\n" +
            "ON r.client_id = c.id\n" +
            "WHERE m.date BETWEEN :from AND :to\n" +
            "GROUP BY  r.client_id\n" +
            "ORDER BY SUM(m.kwh_value) DESC\n" +
            "LIMIT 10",nativeQuery = true)
    List<Client> findTopTenConsumersBetweenDates(LocalDate from, LocalDate to);
}
