package ee.ut.cs.wad2018.fall.springbootdemo.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilTest {

    @Mock
    private InjectableService injectableService;

    @InjectMocks
    private SecurityUtil securityUtil;

    @Test
    public void testGetValueReturnsElse() {
        when(injectableService.getAmount()).thenReturn(2);
        String value = securityUtil.getValue();
        assertEquals("else", value);
    }

    @Test
    public void testGetValueReturnsElseWhenInjectableServiceReturnsNull() {
        when(injectableService.getAmount()).thenReturn(null);
        String value = securityUtil.getValue();
        assertEquals("else", value);
    }

}