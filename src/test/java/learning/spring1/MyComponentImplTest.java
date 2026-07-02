package learning.spring1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MyComponentImplTest {

    @Autowired
    MyComponent myComponent;

    @Autowired
    MyDataRepository repo;

    @BeforeEach
    void deleteEverything() {
        repo.deleteAll();
    }

    @Test
    void addEntry() {
        myComponent.addEntry("Max", "88005553535");
        final List<MyData> allData = repo.findAll();
        assertEquals(1, allData.size());
        assertEquals("Max", allData.get(0).getName());
        assertEquals("88005553535", allData.get(0).getNumber());
    }

    @Test
    void addEntryNullName() {
        myComponent.addEntry(null, "88005553535");
        final List<MyData> allData = repo.findAll();
        assertEquals(0, allData.size());
    }

    @Test
    void searchByName() {
        myComponent.addEntry("Max", "88005553535");
        final List<MyData> searchData = myComponent.search("Max");
        assertEquals(1, searchData.size());
        assertEquals("Max", searchData.get(0).getName());
        assertEquals("88005553535", searchData.get(0).getNumber());
    }

    @Test
    void searchByNumber() {
        myComponent.addEntry("Max", "88005553535");
        final List<MyData> searchData = myComponent.search("5553535");
        assertEquals(1, searchData.size());
        assertEquals("Max", searchData.get(0).getName());
        assertEquals("88005553535", searchData.get(0).getNumber());
    }

    @Test
    void getAllData() {
        myComponent.addEntry("Max", "88005553535");
        final List<MyData> searchData = myComponent.getAllData();
        assertEquals(1, searchData.size());
        assertEquals("Max", searchData.get(0).getName());
        assertEquals("88005553535", searchData.get(0).getNumber());
    }
}
