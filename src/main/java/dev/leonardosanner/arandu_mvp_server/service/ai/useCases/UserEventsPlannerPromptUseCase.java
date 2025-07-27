package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

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

    public HashMap<String, String> execute(String userEmail) {
        List<UserEventInfoDTO> userEventsInformation = this.findUserEventsUseCase.execute(userEmail);
        HashMap<String, String> promptHash = new HashMap<>();

        if (userEventsInformation.isEmpty()) {

            throw new RuntimeException(
                    "You do not have any event registered, you must have at least one to access this resource"
            );
        }

        AtomicInteger count = new AtomicInteger(1);
        String promptUserInformation = userEventsInformation.stream().map(
                event->{
                    String msg = count.get() + ". " + getAttributesInformation(event).toString();
                    count.incrementAndGet();
                    return msg;
                }
        ).collect(Collectors.joining("\n"));

        String systemPrompt = "You are an assistant specialized in creating detailed user plans based on event data. " +
                "Your role is to provide clear, practical, and helpful guidance to help users prepare effectively " +
                "for their upcoming events.";


        String userPrompt = "You must provide a detailed plan for the user based on the events listed below.\n\n" +
                "Each event is represented as a set of key=value pairs in a hash/map format, corresponding to a specific event.\n\n" +
                "The plan should include:\n" +
                "- How the user can prepare for each event,\n" +
                "- Tips and recommendations for effective preparation,\n" +
                "- Possible concerns or challenges that might arise,\n" +
                "- A suggested timeline indicating how many days in advance the user should start preparing,\n" +
                "- Resources or tools that might help the user get ready for each event.\n\n" +
                "Be a helpful guide, offering clear and practical advice tailored to each event.\n\n" +
                "After providing the detailed plans for each event, give a concise summary highlighting the most important points " +
                "the user should keep in mind overall and the timeline for each event.\n\n" +
                "The language you should answer should be the same as the events are informed.\n\n" +
                "Here are the events:\n\n" +
                promptUserInformation;

        promptHash.put("system", systemPrompt);
        promptHash.put("user", userPrompt);

        return promptHash;
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
