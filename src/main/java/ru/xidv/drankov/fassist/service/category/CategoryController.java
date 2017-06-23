/*
 * Copyright 2017 Pavel Drankov, Sergey Shershakov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.xidv.drankov.fassist.service.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.xidv.drankov.fassist.dm.dao.Category;
import ru.xidv.drankov.fassist.dm.dao.User;
import ru.xidv.drankov.fassist.dm.dmr.CategoryRepository;
import ru.xidv.drankov.fassist.ser.category_jsons.*;
import ru.xidv.drankov.fassist.ser.shared_jsons.TokenJSON;
import ru.xidv.drankov.fassist.service.auth.AuthService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.xidv.drankov.fassist.util.Response.error_code_0;
import static ru.xidv.drankov.fassist.util.Response.error_code_1;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final Logger log = LoggerFactory.getLogger(CategoryController.class.getName());

    private AuthService authService;
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @RequestMapping(value = "/new_parent", method = RequestMethod.POST)
    ResponseEntity<?> new_parent(
            @RequestBody AuthNameDescTypeJSON JSON
    ) {
        log.info("category new_parent call " + JSON);
        User user;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_0();
        }

        Category category = new Category(JSON.getType(), JSON.getName(), null, user);
        categoryRepository.save(category);

        return new ResponseEntity<>(new ErrorCodeCategoryJSON(category.getId()), HttpStatus.OK);
    }


    @RequestMapping(value = "/new_child", method = RequestMethod.POST)
    ResponseEntity<?> new_child(
            @RequestBody AuthNameDescTypeParentJSON JSON
    ) {
        log.info("category new_child call " + JSON);
        User user;
        Category parent;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
            parent = categoryService.getCategory(JSON.getParent_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_0();
        }

        Category category = new Category(parent.getType(), JSON.getName(), parent, user);
        categoryRepository.save(category);

        return new ResponseEntity<>(new ErrorCodeCategoryJSON(category.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<?> delete(
            @RequestBody AuthCategoryJSON JSON
    ) {
        log.info("category delete call " + JSON);
        User user;
        Category category_to_delete;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
            category_to_delete = categoryService.getCategory(JSON.getCategory_id());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        //delete category and its subcategories
        recursiveDel(categoryRepository, category_to_delete);

        return error_code_0();
    }


    public static void recursiveDel(CategoryRepository categoryRepository, Category category) {
        category.getSubcategories()
                .forEach(s -> {
                    recursiveDel(categoryRepository, s);
                });

        categoryRepository.delete(category);
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    ResponseEntity<?> list(
            @RequestBody TokenJSON JSON
    ) {
        log.info("category list call " + JSON);
        User user;
        List<Category> categories;
        try {
            user = authService.getUserByToken(JSON.getAuth_token());
            categories = categoryService.getCategories(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return error_code_1();
        }

        List<CategoryJSON> collect = categories.stream()
                .map(Category::get_as_json)
                .collect(Collectors.toList());


        return new ResponseEntity<>(new ErrorCodeAndCat_ListJSON(collect), HttpStatus.OK);
    }

    @Autowired
    public CategoryController(AuthService authService, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.authService = authService;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }
}
