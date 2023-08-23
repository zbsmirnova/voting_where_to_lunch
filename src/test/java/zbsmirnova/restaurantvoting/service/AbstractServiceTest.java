package zbsmirnova.restaurantvoting.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.getRootCause;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
    protected static final Logger log = getLogger("result");

    public  static StringBuilder results = new StringBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public StopwatchImpl stopwatch = new StopwatchImpl();
}
