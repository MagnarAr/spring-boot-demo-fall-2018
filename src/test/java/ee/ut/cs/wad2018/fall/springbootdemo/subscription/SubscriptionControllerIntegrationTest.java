package ee.ut.cs.wad2018.fall.springbootdemo.subscription;

import ee.ut.cs.wad2018.fall.springbootdemo.security.SecurityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
@Import(SecurityConfiguration.class)
public class SubscriptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpMessageSendingOperations messagingTemplate;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Captor
    private ArgumentCaptor<Subscription> subscriptionArgumentCaptor;

    @Captor
    private ArgumentCaptor<SubscriptionDTO> subscriptionDTOArgumentCaptor;

    @Test
    public void testSubscriptionGetMainPage() throws Exception {
        List<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setEmail("testemail@mail.ee");
        subscription.setName("Magnar");
        subscriptionList.add(subscription);

        when(subscriptionRepository.findAll()).thenReturn(subscriptionList);

        this.mockMvc.perform(get("/"))
                // just for debugging purpose
                .andDo(print())
                .andExpect(model().attribute("subscriptions", subscriptionList))
                .andExpect(status().isOk())
                .andExpect(view().name("subscription-home"))
                .andReturn();
    }

    @Test
    public void testSendSubscription() throws Exception {
        this.mockMvc.perform(post("/")
                .param("name", "Magnar")
                .param("email", "magnar.aruoja@gmail.com"))
                // just for debugging purpose
                // .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();

        verify(subscriptionRepository).save(subscriptionArgumentCaptor.capture());
        Subscription capturedEntity = subscriptionArgumentCaptor.getValue();
        assertEquals("Magnar", capturedEntity.getName());
        assertEquals("magnar.aruoja@gmail.com", capturedEntity.getEmail());
        assertNull(capturedEntity.getSubscriptionId());

        verify(messagingTemplate).convertAndSend(eq("/topic/subscriptions"), subscriptionDTOArgumentCaptor.capture());
        SubscriptionDTO capturedDTO = subscriptionDTOArgumentCaptor.getValue();

        assertEquals("Magnar", capturedDTO.getName());
        assertEquals("magnar.aruoja@gmail.com", capturedDTO.getEmail());
    }
}