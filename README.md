# Foursquare-OpenAPI

## What

Repo for:
1. [OpenAPI](https://swagger.io/docs/specification/v3_0/about/) specification files of [Foursquare APIs](https://docs.foursquare.com/)
2. Java Client autogeneration from OpenAPI specification files using [OpenAPI Generator](https://openapi-generator.tech/)

## Why
1. To provide a machine-readable specification of Foursquare APIs
2. To provide a out-of-box, ready to use, Java Client for Foursquare APIs
3. To provide a reference for other languages to [autogenerate clients](https://openapi-generator.tech/docs/generators/#client-generators) in any language

## Usage

### Maven

1. Set up your GitHub credentials in Maven settings file (default: `~/.m2/settings.xml`):
````xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USER_NAME_HERE</username>
            <password>YOUR_GITHUB_TOKEN_HERE</password>
        </server>
    </servers>
</settings>
````
:warning: Consider encrypting your token following these [Maven instructions](https://maven.apache.org/guides/mini/guide-encryption.html).

2. Add the GitHub Packages repository to project `pom.xml` file:
````xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub</name>
        <url>https://maven.pkg.github.com/MatheusArleson/*</url>
    </repository>
</repositories>
````

3. Add the dependency to project `pom.xml` file:
````xml
 <dependencies>
    <dependency>
        <groupId>com.acme.oas.foursquare</groupId>
        <artifactId>API_NAME_YOU_WANT_HERE-api-java-client</artifactId>
        <version>CHECK_LATEST_VERSION_AND_PLACE_IT_HERE</version>
    </dependency>
</dependencies>
````

4. Use the client in your code - Places API example:
````java
import com.acme.oas.foursquare.placesapi.v2025_06_17.api.PlacesApi;
import com.acme.oas.foursquare.placesapi.v2025_06_17.invoker.ApiClient;
import com.acme.oas.foursquare.placesapi.v2025_06_17.invoker.ApiException;
import com.acme.oas.foursquare.placesapi.v2025_06_17.model.PlacesSearchResponse;

import java.net.http.HttpClient;

public class Main {
    
    // IF NEEDED, you can set a custom base path for the API
    //private static final String API_BASE_PATH = "https://places-api.foursquare.com/places/search";
    private static final String API_VERSION = "2025-06-17";
    private static final String API_TOKEN = "SET_YOUR_API_TOKEN_HERE";

    private static final double longitude = 0; // Example: -73.935242
    private static final double latitude = 0;  // Example: 40.730610

    public static void main(String[] args) throws ApiException {
        String llReqParam = latitude + "," + longitude;
        PlacesApi.APIPlacesSearchRequest req = PlacesApi.APIPlacesSearchRequest.newBuilder()
                .xPlacesApiVersion(API_VERSION)
                .ll(llReqParam)
                .build();

        ApiClient apiClient = new ApiClient();
        //apiClient.setBasePath(API_BASE_PATH);

        apiClient.setRequestInterceptor(reqBuilder -> reqBuilder
                .header("Authorization", "Bearer " + API_TOKEN)
        );

        PlacesApi placesApi = new PlacesApi(apiClient);
        PlacesSearchResponse resp = placesApi.placesSearch(req);

        System.out.println(resp);
    }
}
````

## Contributing

### Tooling
1. Git
2. Java 21
3. Maven
4. Any IDE (e.g. IntelliJ)

### Steps
1. [Clone](file:///D:/dev/Git/mingw64/share/doc/git-doc/git-clone.html) the repo
````shell
git clone https://github.com/MatheusArleson/Foursquare-OpenAPI.git
````

2. Change directory to the cloned repo
````shell
cd Foursquare-OpenAPI
````

3. Do a Maven install
````shell
mvn clean install
````

4. Create a git branch for your changes
````shell
git checkout -b YOUR_BRANCH_NAME_HERE
````

5. Make your changes (e.g. add a new OpenAPI specification file and/or autogenerate a new Java Client)

6. Commit your changes
````shell
git add .
git commit -m "YOUR_COMMIT_MESSAGE_HERE"
````

7. Push your changes to your remote branch
````shell
git push origin YOUR_BRANCH_NAME_HERE
````

8. Open a Pull Request on GitHub
9. After review, if everything is ok, your changes will be merged to the `main` branch