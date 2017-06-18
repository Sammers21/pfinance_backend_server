package ru.xidv.drankov.fassist.dm.dmr;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.xidv.drankov.fassist.dm.dao.Category;
import ru.xidv.drankov.fassist.dm.dao.User;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);

    List<Category> findByUser(User u);
}
