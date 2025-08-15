package com.acme.oas.foursquare.placesapi;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PlacesApiOasFilesValidationTest {

    @ParameterizedTest
    @MethodSource("com.acme.oas.foursquare.placesapi.PlacesApiOas#getAllGeneratedOasFiles")
    void shouldValidateOasFiles(File generatedOasFile) throws Exception {
        //GIVEN
        try (InputStream is = new FileInputStream(generatedOasFile)) {
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
