package ee.ut.cs.wad2018.fall.springbootdemo.subscription;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private Model model;

    @Test
    public void testModelIsPopulatedWithDataFromDB() {
        // mocked data
        List<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setEmail("testemail@mail.ee");
        subscription.setName("Magnar");
        subscriptionList.add(subscription);

        when(subscriptionRepository.findAll()).thenReturn(subscriptionList);
        String template = subscriptionController.getMainPage(model);

        assertEquals("subscription-home", template);
        verify(model).addAttribute(eq("subscriptions"), eq(subscriptionList));

    }

}