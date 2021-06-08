package net.pelozo.FinalTPLab5DB2.repository;

import net.pelozo.FinalTPLab5DB2.model.Residence;
import net.pelozo.FinalTPLab5DB2.model.dto.ResidenceDto;
import net.pelozo.FinalTPLab5DB2.model.projections.ResidenceView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, Long> {

    Optional<Residence> findByMeterId(Long id);
}
