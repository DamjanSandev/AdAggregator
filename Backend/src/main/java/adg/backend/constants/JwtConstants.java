package adg.backend.constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JwtConstants {

    public static final String SECRET_KEY = System.getenv("JWT_SECRET");
    public static final Long EXPIRATION_TIME = 86400000L;
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
