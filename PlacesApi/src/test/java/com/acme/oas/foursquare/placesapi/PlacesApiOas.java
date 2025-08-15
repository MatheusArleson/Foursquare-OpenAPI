package com.acme.oas.foursquare.placesapi;

import org.junit.jupiter.params.provider.Arguments;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public interface PlacesApiOas {

    String GENERATED_OAS_BASE_DIR = "generated/oas";
    String GENERATED_OAS_FILE_NAME_SUFFIX = "-api.yaml";

    private static List<File> listAllGenerateOasFiles() throws IOException {
        Path basePath = Paths.get(GENERATED_OAS_BASE_DIR);

        try (Stream<Path> stream = Files.walk(basePath)) {
            return stream.filter(path ->
                            !Files.isDirectory(path)
                                    && path.getFileName().toString().endsWith(GENERATED_OAS_FILE_NAME_SUFFIX)
                    ).map(Path::toFile)
                    .toList();
        }
    }

    static Stream<Arguments> getAllGeneratedOasFiles() throws IOException {
        List<File> generatedOasFiles = PlacesApiOas.listAllGenerateOasFiles();
        return generatedOasFiles.stream().map(Arguments::of);
    }


}
