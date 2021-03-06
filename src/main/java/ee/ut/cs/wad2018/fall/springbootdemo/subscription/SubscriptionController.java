package ee.ut.cs.wad2018.fall.springbootdemo.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    SubscriptionController(SubscriptionRepository subscriptionRepository, SimpMessageSendingOperations messagingTemplate) {
        this.subscriptionRepository = subscriptionRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping(path = "/")
    public String getMainPage(Model model) {
        // take all subscriptions from database
        List<Subscription> subscriptionList = subscriptionRepository.findAll();

        // fill template model with all subscriptions
        model.addAttribute("subscriptions", subscriptionList);

        return "subscription-home";
    }

    @PostMapping(path = "/")
    public String sendSubscription(@ModelAttribute SubscriptionDTO subscriptionDTO) {
        // map DTO to entity
        Subscription entity = mapDtoToEntity(subscriptionDTO);

        // save new entity
        subscriptionRepository.save(entity);

        messagingTemplate.convertAndSend("/topic/subscriptions", subscriptionDTO);

        // redirect to home page
        return "redirect:/";
    }

    private Subscription mapDtoToEntity(SubscriptionDTO subscriptionDTO) {
        Subscription subscriptionEntity = new Subscription();
        subscriptionEntity.setName(subscriptionDTO.getName());
        subscriptionEntity.setEmail(subscriptionDTO.getEmail());
        return subscriptionEntity;
    }

}
