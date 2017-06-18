package ru.xidv.drankov.fassist.service.operation;

import io.vertx.core.json.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class OperationControllerTest {


    @Autowired
    MockMvc mvc;

    /**
     * Test case description:
     * <p>
     * 1. create new user vlad
     * 2. get token
     * 3. create one category
     * 4. create one account
     * 5. create two commits
     * 6. add one tag to this commits
     * 7. list all commits with tag "kek" and make sure that this is a 2 commits
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

        //3. create one category
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

        //4. open account
        JsonObject accountJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_new_account")
                .put("type", 1)
                .put("currency", "EUR");

        String contentAsString = mvc.perform(post("/account/open")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andExpect(jsonPath("$.account_id", is(instanceOf(Integer.class))))
                .andReturn().getResponse().getContentAsString();

        Long account_id = new JsonObject(contentAsString).getLong("account_id");


        //5. create 2 commits
        JsonObject commitJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("account_id", account_id)
                .put("category_id", id_of_parent)
                .put("sum", 100);

        String contentAsString1 = mvc.perform(post("/operation/commit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commitJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long op_1_id = new JsonObject(contentAsString1).getLong("op_id");

        String contentAsString2 = mvc.perform(post("/operation/commit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commitJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long op_2_id = new JsonObject(contentAsString2).getLong("op_id");

        // 6. add tag to this two commits

        JsonObject tagJSON1 = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("op_id", op_1_id)
                .put("tag", "kek");

        JsonObject tagJSON2 = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("op_id", op_2_id)
                .put("tag", "kek");


        mvc.perform(post("/operation/add_tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tagJSON1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        mvc.perform(post("/operation/add_tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tagJSON2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        //7. list all commits with tag "kek" and make sure that this is a 2 commits
        JsonObject with_tagJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("op_id", op_2_id)
                .put("tag", "kek");

        String contentAsString3 = mvc.perform(post("/operation/with_tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(with_tagJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        assertTrue(new JsonObject(contentAsString3).getJsonArray("operations").size() == 2);

    }

    /**
     * Test case description:
     * <p>
     * 1. create new user vlad
     * 2. get token
     * 3. create one category
     * 4. create one account
     * 5. create one commit
     * 6. add notes to commit
     * 7. add memo to commit
     * 8. check that memo and notice have correct value
     * <p>
     * * - error code in each request must be 0
     */
    @Test
    public void testCase2() throws Exception {

        // 1. create user new vlad
        JsonObject logAndPas = new JsonObject()
                .put("login", "vlad2")
                .put("password", "123456789");

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

        //3. create one category
        JsonObject new_par_JSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_money kek")
                .put("description", "my own money ekek")
                .put("type", 1);

        String st = mvc.perform(post("/category/new_parent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new_par_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long id_of_parent = new JsonObject(st).getLong("category_id");

        //4. open account
        JsonObject accountJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_new_account 23")
                .put("type", 1)
                .put("currency", "EUR");

        String contentAsString = mvc.perform(post("/account/open")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andExpect(jsonPath("$.account_id", is(instanceOf(Integer.class))))
                .andReturn().getResponse().getContentAsString();

        Long account_id = new JsonObject(contentAsString).getLong("account_id");


        //5. create one commit
        JsonObject commitJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("account_id", account_id)
                .put("category_id", id_of_parent)
                .put("sum", 100);

        String contentAsString1 = mvc.perform(post("/operation/commit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commitJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        Long op_1_id = new JsonObject(contentAsString1).getLong("op_id");


        //6. add notes to commit
        JsonObject tagJSON2 = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("op_id", op_1_id)
                .put("notes", "kek");

        mvc.perform(post("/operation/add_notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tagJSON2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));


        // 7. add memo to commit
        JsonObject tagJSON3 = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("op_id", op_1_id)
                .put("memo", "kek");

        mvc.perform(post("/operation/add_memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tagJSON3.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        //8. check that memo and notice have correct value
        JsonObject listJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("account_id", account_id);

        /*String contentAsString2 = mvc.perform(post("/account/op_list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(listJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();

        String notes_field = new JsonObject(contentAsString2)
                .getJsonArray("operations")
                .getJsonObject(0)
                .getString("notes");

        assertThat(notes_field, is("kek"));

        String memo_field = new JsonObject(contentAsString2)
                .getJsonArray("operations")
                .getJsonObject(0)
                .getString("memo");

        assertThat(memo_field, is("kek"));*/
    }

}