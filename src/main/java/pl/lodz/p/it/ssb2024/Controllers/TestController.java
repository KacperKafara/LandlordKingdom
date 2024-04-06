package pl.lodz.p.it.ssb2024.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public String test() {
        return "Test1";
    }

}
