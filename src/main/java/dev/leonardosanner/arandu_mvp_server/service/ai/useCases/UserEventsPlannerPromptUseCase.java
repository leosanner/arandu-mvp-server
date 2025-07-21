package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

// Receive a list of events and return a prompt to instruct the
// model to plan the user lif until the event

import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUserEventsUseCase;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class UserEventsPlannerPromptUseCase {

    private final FindUserEventsUseCase findUserEventsUseCase;

    public UserEventsPlannerPromptUseCase(FindUserEventsUseCase findUserEventsUseCase) {
        this.findUserEventsUseCase = findUserEventsUseCase;
    }

    public String execute(String userEmail) {
        List<UserEventInfoDTO> userEventsInformation = this.findUserEventsUseCase.execute(userEmail);
        String prompt;

        if (userEventsInformation.isEmpty()) {
            prompt = "You do not have any event registered, you must have at least one to access this resource";

            throw new RuntimeException(prompt);
        }

        AtomicInteger count = new AtomicInteger(1);
        String promptUserInformation = userEventsInformation.stream().map(
                event->{
                    String msg = count.get() + ". " + getAttributesInformation(event).toString();
                    count.incrementAndGet();
                    return msg;
                }
        ).collect(Collectors.joining("\n"));

         prompt = "You must provide a detailed plan for the user based on the events listed below.\n\n" +
                "Each event is represented as a set of key=value pairs in a hash/map format, corresponding to a specific event.\n\n" +
                "The plan should include:\n" +
                "- How the user can prepare for each event,\n" +
                "- Tips and recommendations for effective preparation,\n" +
                "- Possible concerns or challenges that might arise,\n" +
                "- A suggested timeline indicating how many days in advance the user should start preparing,\n" +
                "- Resources or tools that might help the user get ready for each event.\n\n" +
                "Be a helpful guide, offering clear and practical advice tailored to each event.\n\n" +
                 "After providing the detailed plans for each event," +
                 "give a concise summary highlighting the most important points the user should keep in mind overall" +
                 "and the timeline for each event.\n\n" +
                "Here are the events:\n\n" +
                promptUserInformation;


        return prompt;
    }

    private HashMap<String, String> getAttributesInformation(UserEventInfoDTO userEventInfoDTO) {
        Field[] fields = userEventInfoDTO.getClass().getDeclaredFields();
        HashMap<String, String> userInformation = new HashMap<>();

        for (Field field: fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(userEventInfoDTO);
                userInformation.put(field.getName(), value != null ? value.toString() : "null");

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return userInformation;
    }
}
