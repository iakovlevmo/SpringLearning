package learning.spring1;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final MyComponent myComponent;

    public ApiController(MyComponent myComponent) {
        this.myComponent = myComponent;
    }

    @PostMapping("/add")
    public void addPhoneNumbers(@RequestBody List<MyData> myData) {
        for (MyData myDatum : myData) {
            myComponent.addEntry(myDatum.getName(), myDatum.getNumber());
        }
    }

    @GetMapping("/search")
    public List<MyData> searchPhoneNumbers(@RequestParam String search) {
        List<MyData> results = myComponent.search(search);
        return results;
    }
}
