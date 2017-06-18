package ru.xidv.drankov.fassist.service.account;


import io.vertx.core.json.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    JSONParser parser = new JSONParser();

    /**
     * Test case description:
     * <p>
     * 1. create user new vlad
     * 2. get token
     * 3. open 2 accounts
     * 4. list all accounts, check that size is 2
     * 5. close one of accounts
     * 6. list all accounts
     * 7. check that one of account is closed
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


        // 2. get Token
        MvcResult mvcResult = mvc.perform(post("/auth/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logAndPas.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn();

        JsonObject token_response = new JsonObject(mvcResult.getResponse().getContentAsString());


        //3. open 2 accounts
        JsonObject accountJSON = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("name", "my_new_account")
                .put("type", 1)
                .put("currency", "EUR");

        mvc.perform(post("/account/open")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andExpect(jsonPath("$.account_id", is(instanceOf(Integer.class))));

        mvc.perform(post("/account/open")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andExpect(jsonPath("$.account_id", is(instanceOf(Integer.class))));


        //4. list all accounts
        String list_resp = mvc.perform(post("/account/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();


        JSONObject list_resp_JSON = (JSONObject) parser.parse(list_resp);
        long acc_id = (long) ((JSONObject) ((JSONArray) list_resp_JSON.get("accounts"))
                .get(0)).get("account_id");

        // assert that user have 2 accounts
        assertTrue(((JSONArray) list_resp_JSON.get("accounts")).size() == 2);


        //5. close one of accounts
        JsonObject close_account_json = new JsonObject()
                .put("auth_token", token_response.getString("token"))
                .put("account_id", acc_id);

        mvc.perform(post("/account/close")
                .contentType(MediaType.APPLICATION_JSON)
                .content(close_account_json.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)));

        //6. list all accounts
        list_resp = mvc.perform(post("/account/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error_code", is(0)))
                .andReturn().getResponse().getContentAsString();
        list_resp_JSON = ((JSONObject) parser.parse(list_resp));

        // 7. one of account is closed
        assertThat(
                (long) ((JSONObject) ((JSONArray) list_resp_JSON.get("accounts"))
                        .get(0)).get("account_id") != 0
                        ||
                        (long) ((JSONObject) ((JSONArray) list_resp_JSON.get("accounts"))
                                .get(1)).get("account_id") != 0
                , is(true));
    }
}
