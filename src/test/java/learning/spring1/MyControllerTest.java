package learning.spring1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MyComponent myComponent;

    @Test
    void FrontPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-page"));
    }

    @Test
    void PageOneContainsAddUserLabel() throws Exception {
        when(myComponent.getAllData()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/add-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Add user")));
    }

    @Test
    void PageOneContainsNameInput() throws Exception {
        when(myComponent.getAllData()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/add-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Enter name:")));
    }

    @Test
    void PageOneContainsPhoneInput() throws Exception {
        when(myComponent.getAllData()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/add-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Enter phone number:")));
    }

    @Test
    void PageOneWithHistoryRedirectsBack() throws Exception {
        mockMvc.perform(post("/add-page")
                        .param("name", "Alice")
                        .param("phone", "1234567890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-page"));
    }

    @Test
    void PageTwoContainsSearchLabel() throws Exception {
        mockMvc.perform(get("/search-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Enter name/phone number:")));
    }

    @Test
    void PageTwoContainsSearchButton() throws Exception {
        mockMvc.perform(get("/search-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Search")));
    }

    @Test
    void PageTwoWithHistoryShowsNoResults() throws Exception {
        when(myComponent.search(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/search-page")
                        .param("search", "nobody"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No Results Found")));
    }

    @Test
    void PageTwoWithHistoryShowsSearchTerm() throws Exception {
        when(myComponent.search(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/search-page")
                        .param("search", "testquery"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("testquery")));
    }

    @Test
    void PageThreeContainsSearchResultsHeader() throws Exception {
        mockMvc.perform(get("/results-page"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Search Results")));
    }

    @Test
    void PageThreeWithHistoryShowsNoResults() throws Exception {
        when(myComponent.search(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/results-page")
                        .param("search", "nobody"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No Results Found")));
    }

    @Test
    void PageThreeWithHistoryShowsGoBackLink() throws Exception {
        when(myComponent.search(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/results-page")
                        .param("search", "nobody"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Go back")));
    }
}
