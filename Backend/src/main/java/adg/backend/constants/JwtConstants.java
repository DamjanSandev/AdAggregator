package adg.backend.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JwtConstants {

    public static final String SECRET_KEY;
    public static final Long EXPIRATION_TIME = 86400000L;
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    static {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("jwt.env")) {
            props.load(fis);
            SECRET_KEY = props.getProperty("JWT_SECRET");
            if (SECRET_KEY == null || SECRET_KEY.isBlank()) {
                throw new RuntimeException("Missing JWT_SECRET in jwt.env");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load jwt.env file", e);
        }
    }
}
