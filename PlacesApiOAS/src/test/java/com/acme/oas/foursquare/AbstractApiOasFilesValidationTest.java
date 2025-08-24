package com.acme.oas.foursquare;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//TODO move this test to a global module so all OAS Tests can benefit from it
public abstract class AbstractApiOasFilesValidationTest {

    @ParameterizedTest
    @MethodSource("com.acme.oas.foursquare.ApiOasTest#getAllGeneratedOasFiles")
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
