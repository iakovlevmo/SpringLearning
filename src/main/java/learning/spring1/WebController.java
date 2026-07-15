package learning.spring1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    private final MyComponent myComponent;

    public WebController(MyComponent myComponent) {
        this.myComponent = myComponent;
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
