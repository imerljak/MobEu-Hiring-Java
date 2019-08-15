package com.mobiquityinc.packer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.mobiquityinc.exception.APIException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * PackerTest
 */
public class PackerTest {

    @Test
    public void shouldThrowIfFileNotExists() {
        assertThrows(APIException.class, () -> Packer.pack("nonexistant_input.txt"));
    }

    @Test
    public void shouldNotThrowIfFileExists() {
        assertDoesNotThrow(() -> Packer.pack(getAbsoluteResourcePath("test_input1.txt")));
    }

    @Test
    public void shouldNotReturnNull() throws FileNotFoundException, APIException, URISyntaxException {
        String result = Packer.pack(getAbsoluteResourcePath("test_input1.txt"));
        assertNotNull(result);
    }

    @ParameterizedTest
    @MethodSource("provideTestInputs")
    public void shouldPassTestInputs(String file, String expected) throws FileNotFoundException, APIException, URISyntaxException {
        final String actual = Packer.pack(getAbsoluteResourcePath(file));
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideTestInputs() {
        return Stream.of(
                Arguments.of("test_input1.txt", "4\n-\n2,7\n8,9"),
                Arguments.of("test_input2.txt", "2\n1\n2,4\n9")
        );
    }

    private String getAbsoluteResourcePath(String string) throws URISyntaxException, FileNotFoundException {
        URL url = PackerTest.class.getClassLoader().getResource(string);
        
        if (url == null) {
            throw new FileNotFoundException(string);
        }
        
        return Paths.get(url.toURI()).toFile().getAbsolutePath();
    }
}