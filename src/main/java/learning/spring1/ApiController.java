package learning.spring1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
