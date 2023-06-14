package ru.drdrapp.drappogram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "z")
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='loggedUserName']/span").string("z"));
    }

    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='messagesCards']/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/messages").param("tagFilter", "tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='messagesCards']/div/div").nodeCount(2))
                .andExpect(xpath("//*[@id='messagesCards']/div/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='messagesCards']/div/div[@data-id='3']").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/messages")
                .file("messageFile", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new one");

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='messagesCards']/div/div").nodeCount(5))
                .andExpect(xpath("//*[@id='messagesCards']/div/div[@data-id='10']").exists())
                .andExpect(xpath("//*[@id='messagesCards']/div/div[@data-id='10']/div/p").string("fifth"))
                .andExpect(xpath("//*[@id='messagesCards']/div/div[@data-id='10']/div/div/div").string("new one"));
    }
}