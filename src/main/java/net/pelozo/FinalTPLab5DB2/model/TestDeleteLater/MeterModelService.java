package net.pelozo.FinalTPLab5DB2.model.TestDeleteLater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MeterModelService extends BaseService<MeterModel2>{


    public MeterModelService(JpaRepository<MeterModel2, Long> repository) {
        super(repository);
    }
}
