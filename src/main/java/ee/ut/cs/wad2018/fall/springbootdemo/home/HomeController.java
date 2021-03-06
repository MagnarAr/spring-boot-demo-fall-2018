package ee.ut.cs.wad2018.fall.springbootdemo.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String getTestPage(Principal authToken) {
        return "home/index";
    }

    // Same as @RequestMapping(method = RequestMethod.GET)
    @GetMapping(path = "/test-dynamic")
    public String getTest2Page(
            @RequestParam(value="name-from-url",
                    required=false,
                    defaultValue="World") String nameFromUrl, Model model) {
        model.addAttribute("nameTemplate", nameFromUrl);
        return "home/index_dynamic";
    }

    @GetMapping(path = "/smart-id/login")
    public String getSmartIdLoginPage() {
        return "smartid/login";
    }

    @GetMapping(path = "/secured")
    public String getSecuredPage() {
        return "secured/test";
    }

}
