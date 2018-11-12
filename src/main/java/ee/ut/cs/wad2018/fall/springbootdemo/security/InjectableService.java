package ee.ut.cs.wad2018.fall.springbootdemo.security;

import org.springframework.stereotype.Service;

@Service
public class InjectableService {

    public Integer getAmount() {
        return 2;
    }

}
