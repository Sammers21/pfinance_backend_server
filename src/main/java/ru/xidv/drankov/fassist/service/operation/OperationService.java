package ru.xidv.drankov.fassist.service.operation;

import io.vertx.core.AbstractVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xidv.drankov.fassist.dm.dao.Operation;
import ru.xidv.drankov.fassist.dm.dmr.OperationRepository;

@Service
public class OperationService extends AbstractVerticle {

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    private OperationRepository operationRepository;

    public Operation getOperation(long id) throws Exception {

        Operation byId = operationRepository.findById(id);
        if (byId == null) {
            throw new Exception("Operation with id " + id + " not found");
        }

        return byId;
    }
}
