import lombok.Data;

@Data
public class Person {
    private Integer id;
    private Integer age;
    private Integer height;
    private String name;

    public Person(Integer id, Integer age, Integer height, String name) {
        this.id = id;
        this.age = age;
        this.height = height;
        this.name = name;
    }

    public Person() {
    }
}