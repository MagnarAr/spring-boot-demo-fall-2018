package ee.ut.cs.wad2018.fall.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {

    private final InjectableService injectableService;

    @Autowired
    SecurityUtil(InjectableService injectableService) {
        this.injectableService = injectableService;
    }

    public String getValue() {
        Integer amount = injectableService.getAmount();
        if (new Integer(3).equals(amount)) {
            return "kolm";
        } else {
            return "else";
        }
    }

}
