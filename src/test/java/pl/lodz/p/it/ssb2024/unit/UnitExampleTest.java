package pl.lodz.p.it.ssb2024.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.ssb2024.config.ToolConfig;
import pl.lodz.p.it.ssb2024.mok.services.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
public class UnitExampleTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void test() {
        assertEquals("test", userService.test());
    }
}
