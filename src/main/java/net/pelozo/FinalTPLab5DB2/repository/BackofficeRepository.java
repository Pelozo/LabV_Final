package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Backoffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackofficeRepository extends JpaRepository<Backoffice, Long> {

    Backoffice findByUsernameAndPassword(String username, String password);

}
