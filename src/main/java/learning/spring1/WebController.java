package learning.spring1;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class WebController {
    private final MyComponent myComponent;
    private final RestClient restClient;
    private final Environment environment;

    public WebController(MyComponent myComponent, RestClient restClient, Environment environment) {
        this.myComponent = myComponent;
        this.restClient = restClient;
        this.environment = environment;
    }

    private String baseUrl() {
        String port = environment.getProperty("local.server.port");
        if (port == null || port.equals("0")) {
            port = environment.getProperty("server.port", "8080");
        }
        return "http://localhost:" + port;
    }

    @GetMapping(path = "")
    public String FrontPage() {
        return "redirect:/add-page";
    }

    @GetMapping(path = "/add-page")
    public String PageOne(Model model) {
        model.addAttribute("allData", myComponent.getAllData());
        return "AddPage";
    }

    @PostMapping(path = "/add-page")
    public String PageOneWithHistory(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String phone) {
        myComponent.addEntry(name, phone);
        return "redirect:/add-page";
    }


    @GetMapping(path = "/add-page-rest")
    public String PageOneRest(Model model) {
        model.addAttribute("allData", myComponent.getAllData());
        return "AddPageRest";
    }

    @PostMapping(path = "/add-page-rest")
    public String PageOneRestWithHistory(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String phone) {
        MyData entry = new MyData(name, phone);

        restClient.post()
                .uri(baseUrl() + "/api/add")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(List.of(entry))
                .retrieve()
                .toBodilessEntity();

        return "redirect:/add-page-rest";
    }

    @GetMapping(path = "/search-page")
    public String PageTwo() { return "SearchPage"; }

    @PostMapping(path = "/search-page")
    public String PageTwoWithHistory(@RequestParam(required = false) String search, Model model) {
        List<MyData> results = myComponent.search(search);
        model.addAttribute("lastSearch", search);
        model.addAttribute("results", results);
        model.addAttribute("noResults", results.isEmpty());
        return "SearchPage";
    }

    @GetMapping(path = "/search-page-rest")
    public String PageTwoRest() { return "SearchPageRest"; }

    @PostMapping(path = "/search-page-rest")
    public String PageTwoRestWithHistory(@RequestParam(required = false) String search,
                                         Model model) {
        String uri = UriComponentsBuilder
                .fromUriString(baseUrl() + "/api/search")
                .queryParam("search", search)
                .toUriString();

        List<MyData> results = restClient.get()
                .uri(uri)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<MyData>>() {});

        model.addAttribute("lastSearch", search);
        model.addAttribute("results", results);
        model.addAttribute("noResults", results == null || results.isEmpty());
        return "SearchPageRest";
    }

    @GetMapping(path = "/results-page")
    public String PageThree() { return "ResultsPage"; }

    @PostMapping(path = "/results-page")
    public String PageThreeWithHistory(@RequestParam String search, Model model) {
        List<MyData> results = myComponent.search(search);
        model.addAttribute("lastSearch", search);
        model.addAttribute("results", results);
        model.addAttribute("noResults", results.isEmpty());
        return "ResultsPage";
    }
}
