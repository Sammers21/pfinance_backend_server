package ru.xidv.drankov.fassist.dm.dmr;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(long id);
}
