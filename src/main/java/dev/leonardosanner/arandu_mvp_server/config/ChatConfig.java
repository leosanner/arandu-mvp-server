package dev.leonardosanner.arandu_mvp_server.config;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ChatConfig {

    @Value("${NEWS_API_KEY}")
    private String newsApiKey;

    @Value("${ARANDU_EMAIL}")
    private String aranduEmail;

    @Value("${ARANDU_APP_PASSWORD}")
    private String aranduPassword;

    // Mcp config

    @Bean
    public McpSyncClient mcpSyncClient(){

        return McpClient
                .sync(new StdioClientTransport(
                        ServerParameters.builder("node")
                                .args("../../../projetos_integracao_ia/mcp-ts-news/build/index.js")
                                .env(Map.of("NEWS_API_KEY", newsApiKey,
                                        "ARANDU_EMAIL", aranduEmail,
                                        "ARANDU_APP_PASSWORD", aranduPassword))
                                .build()))
                .build();
    };

    // ChatClient config

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 McpSyncClient mcpSyncClient) {

        mcpSyncClient.initialize();

        var toolCallBackProvider = new SyncMcpToolCallbackProvider(mcpSyncClient);

        return builder
                .defaultToolCallbacks(toolCallBackProvider)
                .build();
    }
}
