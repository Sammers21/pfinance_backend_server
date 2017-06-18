package ru.xidv.drankov.fassist.service.category;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xidv.drankov.fassist.dm.dao.Category;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.CategoryRepository;
import ru.xidv.drankov.fassist.dm.dmr.UserRepository;

import java.util.List;

@Service
public class CategoryService extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        vertx.eventBus().consumer("cat_service.new_user", msg -> {
            log.info("msg is "+msg.body().toString());
            User byLogin = userRepository.findByLogin(msg.body().toString());
            if (byLogin == null) {
                log.error("user " + msg.body().toString() + " not found");
                return;
            }

            Category spendings = new Category(-1, "Spendings", null, byLogin);
            Category earnings = new Category(1, "Earnings", null, byLogin);
            categoryRepository.save(spendings);
            categoryRepository.save(earnings);
        });

    }

    private final Logger log = LoggerFactory.getLogger(CategoryService.class.getName());
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category getCategory(long id) throws Exception {
        Category byId = categoryRepository.findById(id);
        if (byId == null) {
            throw new Exception("Category with id " + id + " not found");
        }

        return byId;
    }

    public List<Category> getCategories(User user) throws Exception {
        List<Category> by_user = categoryRepository.findByUser(user);
        if (by_user == null) {
            throw new Exception("User with name " + user.getName() + " not found");
        }

        return by_user;
    }
}
