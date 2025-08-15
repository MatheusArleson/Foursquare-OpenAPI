package com.acme.oas;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GeneratedOasFilesValidationTest {

    private static final String GENERATED_OAS_BASE_DIR = "generated/oas";
    private static final String GENERATED_OAS_FILE_NAME_SUFFIX = "-api.yaml";

    private static List<File> listAllGenerateOasFiles(String baseDir) throws IOException {
        Path basePath = Paths.get(baseDir);

        try (Stream<Path> stream = Files.walk(basePath)) {
            return stream.filter(path ->
                !Files.isDirectory(path)
                && path.getFileName().toString().endsWith(GENERATED_OAS_FILE_NAME_SUFFIX)
            ).map(Path::toFile)
            .toList();
        }
    }

    public static Stream<Arguments> getAllGeneratedOasFiles() throws IOException {
        List<File> generatedOasFiles = listAllGenerateOasFiles(GENERATED_OAS_BASE_DIR);
        return generatedOasFiles.stream().map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("getAllGeneratedOasFiles")
    void shouldValidateOasFiles(File generatedOasFile) throws Exception {
        //GIVEN
        try(InputStream is = new FileInputStream(generatedOasFile)) {
            String fileAsStr = IOUtils.toString(is, StandardCharsets.UTF_8);

            ParseOptions oasParseOpts = new ParseOptions();
            List<AuthorizationValue> oasParseAuthVals = null;

            //WHEN
            SwaggerParseResult oasParseResult = new OpenAPIParser().readContents(fileAsStr, oasParseAuthVals, oasParseOpts);

            // THEN
           assertThat(oasParseResult.getMessages()).isEmpty();
           assertThat(oasParseResult.getOpenAPI()).isNotNull();

        }
    }

}
