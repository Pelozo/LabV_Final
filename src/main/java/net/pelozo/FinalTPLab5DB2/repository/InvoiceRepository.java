package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    @Query(value = "SELECT * " +
            "FROM INVOICES I " +
            "JOIN RESIDENCES R " +
            "ON I.RESIDENCE_ID = R.ID " +
            "JOIN CLIENTS C " +
            "ON  R.CLIENT_ID = :userid "
            ,nativeQuery = true)
    Page<Invoice> findByResidence_Client_Id(@Param("userid")Long id, Pageable pageable);
}
