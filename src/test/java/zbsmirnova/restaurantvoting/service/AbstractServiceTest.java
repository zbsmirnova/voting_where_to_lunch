package zbsmirnova.restaurantvoting.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest { }
