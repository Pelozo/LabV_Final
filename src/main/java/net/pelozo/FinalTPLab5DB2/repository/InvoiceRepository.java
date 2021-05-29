package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

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
    Page<List<Invoice>> findByClient(Long clientId, Date startDate, Date endDate, Pageable pageable);

    Page<List<Invoice>> findByResidence_ClientIdAndIsPaidFalse(Long clientId, Pageable pageable);



}
