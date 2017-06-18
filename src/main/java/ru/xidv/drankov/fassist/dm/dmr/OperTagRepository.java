package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.OperTag;

import java.util.List;

public interface OperTagRepository extends JpaRepository<OperTag, Long> {
    List<OperTag> findByTag(String tag);
}
