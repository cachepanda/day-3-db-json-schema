public class Main {
    public static void main(String[] args) {
        Person person = new Person(1, 24, 179, "Peter");
        Database db = new Database("database.ndjson");
        db.set(person);
        Person incrementAge = new Person(1, 29, 179, "Peter");
        db.set(incrementAge);
        Person response = db.get(1).orElse(new Person(1,1,1,""));
        System.out.println(response);
    }
}