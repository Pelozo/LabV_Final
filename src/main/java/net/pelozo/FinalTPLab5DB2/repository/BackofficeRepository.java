package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import net.pelozo.FinalTPLab5DB2.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackofficeRepository extends JpaRepository<Backoffice, Long> {

    Backoffice findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT I.*\n" +
            "FROM invoices I\n" +
            "JOIN residences R\n" +
            "ON R.id = I.residencec_id\n" +
            "WHERE I.is_paid = FALSE AND R.id = :residenceId AND R.client_id = :clientId;",nativeQuery = true)
    List<Invoice> findUnpaidInvoicesByClientAndResidence(@Param("clientId") long clientId,@Param("residenceId") long residenceId);
}
