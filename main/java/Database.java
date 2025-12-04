import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Database {

    String fileName;
    ObjectMapper objectMapper = new ObjectMapper();
    private Map<Integer, Long> hashIndex;

    public Database(String fileName) {
        this.fileName = fileName;
        this.hashIndex = new HashMap<>();
        populateHashIndex();
    }

    public void populateHashIndex() {
        try (RandomAccessFile file = new RandomAccessFile(this.fileName, "r")) {
            String row;
            while (true) {
                Long pointer = file.getFilePointer();
                row = file.readLine();
                if (row == null){
                    break;
                }
                Person record = objectMapper.readValue(row, Person.class);
                Integer id = record.getId();
                hashIndex.put(id, pointer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Person record) {
        try (RandomAccessFile file = new RandomAccessFile(this.fileName, "rw")) {
            file.seek(file.length());
            String row = objectMapper.writeValueAsString(record) + "\n";
            this.hashIndex.put(record.getId(), file.getFilePointer());
            file.write(row.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> get(Integer id) {
        try (RandomAccessFile file = new RandomAccessFile(this.fileName, "r")) {
            if (this.hashIndex.get(id) == null) {
                return Optional.empty();
            }
            file.seek(this.hashIndex.get(id));
            String row = file.readLine();
            return Optional.of(objectMapper.readValue(row, Person.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
