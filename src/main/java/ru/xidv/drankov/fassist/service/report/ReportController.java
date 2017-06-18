package ru.xidv.drankov.fassist.service.report;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xidv.drankov.fassist.dm.dao.Account;
import ru.xidv.drankov.fassist.dm.dao.Operation;
import ru.xidv.drankov.fassist.ser.account_jsons.ErrorCodeAndOpListJSON;
import ru.xidv.drankov.fassist.ser.account_jsons.TokenAndAcIDRequestJSON;
import ru.xidv.drankov.fassist.ser.operation_jsons.OperationJSON_without_acc;
import ru.xidv.drankov.fassist.service.account.AccountService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.xidv.drankov.fassist.util.Response.error_code_1;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final Logger log = LoggerFactory.getLogger(ReportController.class.getName());


    private final AccountService accountService;

    @Autowired
    public ReportController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/since", method = RequestMethod.POST)
    ResponseEntity<?> since(
            @RequestBody TokenAndAcIDRequestJSON JSON
    ) {
        Account account;
        try {
            account = accountService.getAccountById(JSON.getAccount_id());
        } catch (Exception e) {

            log.error(e.getMessage());
            return error_code_1();
        }

        account.getOperations()
                .stream()
                .forEach(System.out::println);

        List<OperationJSON_without_acc> collect = account.getOperations()
                .stream()
                .filter(s -> s.getTimestamp() >= JSON.getTimestamp_from())
                .map(Operation::get_as_json_t)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorCodeAndOpListJSON(collect), HttpStatus.OK);
    }
}
