package ru.xidv.drankov.fassist.service.category;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    MockMvc mvc;


    /**
     * Test case description:
     * <p>
     * 1. create new user vlad
     * 2. get token
     * 3. create one category and 2 subcategories
     * 4. list all categories
     * 5. delete one of subcategories
     * 6. delete root category
     * <p>
     * * - error code in each request must be 0
     */
    @Test
    public void testCase1() throws Exception {

        // 1. create user new vlad
        JsonObject logAndPas = new JsonObject()
                .put("login", "vlad")
                .put("password", "12345678");

        mvc.perform(post("/auth/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logAndPas.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        // 2. get token
        MvcResult mvcResult = mvc.perform(post("/auth/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logAndPas.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn();

        JsonObject token_response = new JsonObject(mvcResult.getResponse().getContentAsString());

        // 3.1 create parent
        JsonObject new_par_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_money")
                .put("description", "my own money")
                .put("type", 1);

        String st = mvc.perform(post("/category/new_parent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new_par_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long id_of_parent = new JsonObject(st).getLong("category_id");

        // 3.2 create first child
        JsonObject new_child_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_money_1")
                .put("description", "my own money 1")
                .put("type", 1)
                .put("parent_id", id_of_parent);

        st = mvc.perform(post("/category/new_child")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new_child_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long id_of_fst_child = new JsonObject(st).getLong("category_id");

        // 3.3 create second child
        JsonObject new_child_JSON_2 = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_money_2")
                .put("description", "my own money 2")
                .put("type", 1)
                .put("parent_id", id_of_parent);

        st = mvc.perform(post("/category/new_child")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new_child_JSON_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long id_of_sec_child = new JsonObject(st).getLong("category_id");


        //4. list all categories
        JsonObject auth_token_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"));

        st = mvc.perform(post("/category/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(auth_token_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        JsonArray jsonArray1 = new JsonObject(st).getJsonArray("cat_list").getJsonObject(0).getJsonArray("child_list");
        JsonArray jsonArray2 = new JsonObject(st).getJsonArray("cat_list").getJsonObject(1).getJsonArray("child_list");
        JsonArray jsonArray3 = new JsonObject(st).getJsonArray("cat_list").getJsonObject(2).getJsonArray("child_list");

        //check that one of category have 2 children
        assertTrue(jsonArray1.size() == 2 | jsonArray2.size() == 2 | jsonArray3.size() == 2);

        //5. delete one of subcategories
        JsonObject delete_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("category_id", id_of_fst_child);

        //5.1 make sure that one of category is deleted
        mvc.perform(post("/category/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(delete_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        //5.2 make sure it was deleted
        st = mvc.perform(post("/category/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(auth_token_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        jsonArray1 = new JsonObject(st).getJsonArray("cat_list").getJsonObject(0).getJsonArray("child_list");
        jsonArray2 = new JsonObject(st).getJsonArray("cat_list").getJsonObject(1).getJsonArray("child_list");

        assertTrue((jsonArray1.size() == 1 && jsonArray2.size() == 0)
                || (jsonArray2.size() == 1 && jsonArray1.size() == 0));


        //6. delete root category
        JsonObject delete_root_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("category_id", id_of_parent);

        mvc.perform(post("/category/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(delete_root_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        //6.2 make sure it was deleted
        st = mvc.perform(post("/category/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(auth_token_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();
        jsonArray1 = new JsonObject(st).getJsonArray("cat_list");

        assertTrue(jsonArray1.size() == 0);
    }
}
