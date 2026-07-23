package learning.spring1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private MyComponent myComponent;

    @Test
    void addPhoneNumbers() throws Exception {
        String data = "[\n  {\n    \"name\": \"Maxim\",\n    \"number\": \"123\"\n  }\n]";

        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isOk());

        verify(myComponent).addEntry("Maxim", "123");
    }

    @Test
    void searchPhoneNumbers() throws  Exception {
        String search = "Oleg";
        String data = "[\n  {\n    \"name\": \"Oleg\",\n    \"number\": \"456\"\n  }\n]";

        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("search", search))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n  {\n    \"name\": \"Oleg\",\n    \"number\": \"456\"\n  }\n]"));

        verify(myComponent).search("Oleg");
    }
}
