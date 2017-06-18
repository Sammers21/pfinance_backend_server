package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.xidv.drankov.fassist.dm.dao.User;

@Repository
@Transactional(readOnly = false)
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByLogin(String login);
}
