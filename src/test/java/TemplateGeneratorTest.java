import org.example.TemplateGenerator;
import org.example.MissingValueException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@ExtendWith(FileOutputExtension.class)
class TemplateGeneratorTest {
    private TemplateGenerator templateGenerator;

    @TempDir
    File anotherTempDir;

    @BeforeEach

    void setUp() {
        templateGenerator = new TemplateGenerator();
    }

    @ParameterizedTest
    @ValueSource(strings = { "Johns", "pop" })
    void replaceVariablePlaceholderWithValue(String name) throws IOException {
        String template = "Hello, #{name}!";
        String expected = "Hello, " + name + "!";
        String actual = templateGenerator.generate(template, "name", name);
        assertEquals(expected, actual);

        File letters = new File(anotherTempDir, "letters.txt");
        List<String> lines = Arrays.asList("x", "y", "z");

        Files.write(letters.toPath(), lines);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Hello", "Welcome" })
    void supportRuntimeValuesWithDifferentVariableSyntax(String world) {
        String template = "Some text: #{value}";
        String expected = "Some text: " + world + "";
        String actual = templateGenerator.generate(template, "value", world);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " " })
    void throwExceptionIfValueNotProvidedForPlaceholder(String world) {
        String template = "Some text: #{value}";
        assertThrows(MissingValueException.class, () -> {
            templateGenerator.generate(template, "value", world);
        });
    }

    void mockFileAndOutputFromConsole() throws IOException {
        File mockedFile = Mockito.mock(File.class);

        Mockito.when(mockedFile.exists()).thenReturn(true);

        //Verify that the File was created and the exists-method of the mock was called
        Mockito.verify(mockedFile).createNewFile();
    }
}
