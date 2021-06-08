package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    //List<Invoice> findAllByResidence_Client_IdAndIssueDateLessThanEqualAndIssueDateGreaterThanEqual(Long clientId, Date endDate, Date startDate, Pageable pageable);
    //List<Invoice> findAllByResidence_Client_Id(Long clientId);

    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query

    @Query(value = "select i.* from invoices i\n" +
            "left join residences r on i.residence_id = r.id\n" +
            "left join clients c on r.client_id = c.id\n" +
            "where c.id = :clientId\n" +
            "and i.issue_date between :startDate and :endDate\n",
            countQuery = "select count(i.id) from invoices i\n" +
                    "left join residences r on i.residence_id = r.id\n" +
                    "left join clients c on r.client_id = c.id\n" +
                    "where c.id = :clientId\n" +
                    "and i.issue_date between :startDate and :endDate",
            nativeQuery = true)
    Page<Invoice> findByClientBetweenDates(Long clientId, Date startDate, Date endDate, Pageable pageable);

    Page<Invoice> findByResidence_ClientIdAndIsPaidFalse(Long clientId, Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM INVOICES I " +
            "JOIN RESIDENCES R " +
            "ON I.RESIDENCE_ID = R.ID " +
            "JOIN CLIENTS C " +
            "ON  R.CLIENT_ID = :userid "
            ,nativeQuery = true)
    Page<Invoice> findByResidence_Client_Id(@Param("userid")Long id, Pageable pageable);


    @Query(value = "SELECT I.*\n" +
            "FROM invoices I\n" +
            "JOIN residences R\n" +
            "ON R.id = I.residence_id\n"+
            "WHERE I.is_paid = FALSE AND R.id = :residenceId AND R.client_id = :clientId",nativeQuery = true)
    List<Invoice> findUnpaidInvoicesByClientAndResidence(@Param("clientId") long clientId, @Param("residenceId") long residenceId);


}
