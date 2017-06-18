package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrency(String s);
}
