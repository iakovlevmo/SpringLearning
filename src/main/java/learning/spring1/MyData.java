package learning.spring1;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
@Table(name = "records")
public class MyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;

    public MyData() {}

    public MyData(String name, String number) {
        this.name = name;
        this.number = number;
    }

    //public Long getId() { return id; }
    public String getName() { return name; }
    //public void setName(String name) { this.name = name; }
    public String getNumber() { return number; }
    //public void setNumber(String number) { this.number = number; }
}
