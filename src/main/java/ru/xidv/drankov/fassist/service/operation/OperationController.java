package ru.xidv.drankov.fassist.service.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xidv.drankov.fassist.dm.dao.*;
import ru.xidv.drankov.fassist.dm.dmr.OperTagRepository;
import ru.xidv.drankov.fassist.dm.dmr.OperationRepository;
import ru.xidv.drankov.fassist.ser.operation_jsons.*;
import ru.xidv.drankov.fassist.service.account.AccountService;
import ru.xidv.drankov.fassist.service.auth.AuthService;
import ru.xidv.drankov.fassist.service.category.CategoryService;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.xidv.drankov.fassist.util.Response.error_code_0;
import static ru.xidv.drankov.fassist.util.Response.error_code_1;

@RestController
@RequestMapping("/operation")
public class OperationController {
    private final Logger log = LoggerFactory.getLogger(OperationController.class.getName());


    private AuthService authService;
    private CategoryService categoryService;
    private AccountService accountService;
    private OperationRepository operationRepository;
    private OperationService operationService;
    private OperTagRepository operTagRepository;

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    ResponseEntity<?> commit(
            @RequestBody AuthAccountCat_IdSumJSON JSON
    ) {
        log.info("operation call with "+JSON.toString());

        Category category_to_commit;
        Account account;
        try {
            //check it is ok
            authService.getUserByToken(JSON.getAuth_token());
            category_to_commit = categoryService.getCategory(JSON.getCategory_id());
            account = accountService.getAccountById(JSON.getAccount_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        //get last operation balance
        double balanceBefore;
        Optional<Operation> lats_operation = operationRepository
                .findAll()
                .stream()
                .max(Comparator.comparingLong(Operation::getTimestamp));

        balanceBefore = lats_operation.map(Operation::getBalance).orElse(0d);

        log.info(String.valueOf(balanceBefore));

        //create operation
        Operation operation = new Operation(
                category_to_commit,
                account,
                balanceBefore + JSON.getSum(),
                JSON.getSum(),
                "",
                Instant.now().getEpochSecond()
        );

        operationRepository.save(operation);
        return new ResponseEntity<>(new ErrorCodeAndOp_IdJSON(operation.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/add_tag", method = RequestMethod.POST)
    ResponseEntity<?> add_tag(
            @RequestBody AuthOp_IdTagJSON JSON
    ) {
        log.info("account list call "+ JSON);
        Operation operation;
        try {
            authService.getUserByToken(JSON.getAuth_token());
            operation = operationService.getOperation(JSON.getOp_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        OperTag operTag = new OperTag(JSON.getTag(), operation);
        operTagRepository.save(operTag);


        return error_code_0();
    }

    @RequestMapping(value = "/add_memo", method = RequestMethod.POST)
    ResponseEntity<?> add_memo(
            @RequestBody AuthOp_IdMemoJSON JSON
    ) {
        log.info("account add_memo call "+ JSON);
        Operation op_to_memo;
        try {
            authService.getUserByToken(JSON.getAuth_token());
            op_to_memo = operationService.getOperation(JSON.getOp_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        op_to_memo.setMemo(JSON.getMemo());
        operationRepository.save(op_to_memo);

        return error_code_0();
    }

    @RequestMapping(value = "/add_notes", method = RequestMethod.POST)
    ResponseEntity<?> add_notes(
            @RequestBody AuthOp_IdNotesJSON JSON
    ) {
        log.info("account add_notes call "+ JSON);
        Operation op_to_memo;
        try {
            authService.getUserByToken(JSON.getAuth_token());
            op_to_memo = operationService.getOperation(JSON.getOp_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        op_to_memo.setNotes(JSON.getNotes());
        operationRepository.save(op_to_memo);

        return error_code_0();
    }

    @RequestMapping(value = "/with_tag", method = RequestMethod.POST)
    ResponseEntity<?> with_tag(
            @RequestBody AuthTagJSON JSON
    ) {
        log.info("account with_tag call "+ JSON);
        User user;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        List<OperationJSON_without_tag> collect = operTagRepository.findByTag(JSON.getTag())
                .stream()
                .filter(s ->
                        s.getOperation().getAccount().getUser().equals(user)
                )
                .map(OperTag::getOperation)
                .map(Operation::get_as_json_a)
                .collect(Collectors.toList());


        return new ResponseEntity<>(new ErrorCodeAndOperationsJSON(collect), HttpStatus.OK);
    }

    @Autowired
    public OperationController(AuthService authService, CategoryService categoryService, AccountService accountService, OperationRepository operationRepository, OperationService operationService, OperTagRepository operTagRepository) {
        this.authService = authService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.operationRepository = operationRepository;
        this.operationService = operationService;
        this.operTagRepository = operTagRepository;
    }
}
