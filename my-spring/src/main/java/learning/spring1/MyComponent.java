package learning.spring1;

import java.util.List;

public interface MyComponent {
    void addEntry(String name, String phone);
    List<MyData> search(String search);
    List<MyData> getAllData();
}
