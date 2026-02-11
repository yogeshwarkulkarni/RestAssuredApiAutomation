package camel.api.config;

public class TestConfig {
    // Allow override from command line: -DbaseUrl=...
    public static final String BASE_URL =
            System.getProperty("baseUrl", "https://jsonplaceholder.typicode.com");
}
