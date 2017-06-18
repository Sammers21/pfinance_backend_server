package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Operation findById(long id);
}
