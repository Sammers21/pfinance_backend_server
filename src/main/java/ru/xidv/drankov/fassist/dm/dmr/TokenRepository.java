package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xidv.drankov.fassist.dm.dao.SecToken;


@Repository
@Transactional(readOnly = false)
public interface TokenRepository extends JpaRepository<SecToken, Long> {

    SecToken findById(Long id);

    SecToken findByToken(String token);
}
