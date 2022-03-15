package guru.qa.homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.domain.Teacher;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonJsonTest {

    @Test
    void jacksonJsonClassTest () throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/files/simple.json");
        Teacher jsonObject = objectMapper.readValue(file, Teacher.class);
        assertThat(jsonObject.name).isEqualTo("Max");
        assertThat(jsonObject.adress.street).isEqualTo("Belinskogo");
    }
}
