package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

import dev.leonardosanner.arandu_mvp_server.model.dto.BasicResponseDTO;
import dev.leonardosanner.arandu_mvp_server.model.dto.UserEventInfoDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.event.useCases.FindUserEventsUseCase;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendNewsByEmailsUseCase {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private FindUserByCookieUseCase findUserByCookieUseCase;

    @Autowired
    private FindUserEventsUseCase findUserEventsUseCase;

    @Autowired
    private ReportUsageUseCase reportUsageUseCase;

    private static final String SYSTEM_PROMPT = """
            You are a business analysis and executive reporting specialist.
            Your task is to analyze user events and create a single professional report.
            
            CONTEXT:
            - User: %s
            - You should send a email to this user %s
            - You have access to news search and email sending tools

            MANDATORY PROCESS:
            1. EVENT ANALYSIS:
               - Identify major themes from the provided events
               - Group similar events by category/sector
               - Determine relevance and impact of each theme

            2. MARKET RESEARCH:
               - For each identified theme, search for recent news
               - Collect source URLs for reference

            3. REPORT GENERATION:
               - Create ONE professional email in Portuguese
               - Subject: "Relatório Semanal: Análise de Eventos e Tendências."
              \\s
            EMAIL STRUCTURE:
            ```
            Prezado(a) [Name],

            Segue relatório baseado na análise de seus eventos recentes:

            ## Resumo
            [2-3 line synthesis of main insights]

            ## Temas Identificados
            ### 1. [Theme Name]
            - Eventos relacionados: [list]
            - Tendência de mercado: [insight]
            - Notícias relevantes: [links with description]

            ### 2. [Next theme...]

            ## Recomendações
            [2-3 practical actions based on analysis]

            ## Fontes Consultadas
            [Numbered list with URLs and descriptions]

            Atenciosamente,
            Arandu Business
            ```

            IMPORTANT: Always send this email.

            TECHNICAL REQUIREMENTS:
            - Use ONLY Brazilian Portuguese for email content
            - Include clickable source URLs
            - Maximum 800 words in email
            - Professional but accessible tone
            - Focus on actionable insights

            IMPORTANT: Send ONLY one email with all integrated content, the most important task
            is to send the email, never forget that.
            
            Never end this conversation before send the email, you must send it.
           \\s""\";
          """;

    public BasicResponseDTO execute(String cookieValue) {

        UserEntity user = this.findUserByCookieUseCase.execute(cookieValue);
        List<UserEventInfoDTO> userEvents = this.findUserEventsUseCase.execute(user.getEmail());

        this.reportUsageUseCase.execute(user);

        String content = this.chatClient.prompt()
                .system(String.format(SYSTEM_PROMPT, user.getName(),user.getEmail()))
                .user(userEvents.stream().map(
                        this::formatEventToString
                ).collect(Collectors.joining("\n")))
                .call().content();

        System.out.println(content);

        return BasicResponseDTO.builder()
                .success(true)
                .message("Report and news sent.")
                .build();
    }

    private String formatEventToString(UserEventInfoDTO eventInfoDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String template =  """
                Event date: %s;
                Name: %s;
                Description: %s;
                """;

        return String.format(template,
                eventInfoDTO.getStartDate().format(formatter),
                eventInfoDTO.getName(),
                eventInfoDTO.getDescription());
    }
}
