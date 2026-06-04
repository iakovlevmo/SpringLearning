package learning.spring1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyComponentImpl implements MyComponent {
    private static final Logger logger = LoggerFactory.getLogger(MyComponentImpl.class);
    private final MyDataRepository repo;

    public MyComponentImpl(MyDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addEntry(String name, String phone) {
        if (name != null) {
            repo.save(new MyData(name, phone));
        }
    }

    @Override
    public List<MyData> search(String search) {
        return repo.findAll().stream()
                .filter(d -> d.getName().contains(search) || d.getNumber().contains(search))
                .toList();
    }

    @Override
    public List<MyData> getAllData() {
        return repo.findAll();
    }
}
