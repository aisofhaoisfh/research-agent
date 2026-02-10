package com.researchagent.config;

import com.researchagent.tools.FinanceDiffTool;
import com.researchagent.tools.StoreSummaryTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    private final ChatModel chatModel;
    private final StoreSummaryTool storeSummaryTool;
    private final FinanceDiffTool financeDiffTool;

    public ChatClientConfig(
            ChatModel chatModel,
            StoreSummaryTool storeSummaryTool,
            FinanceDiffTool financeDiffTool) {
        this.chatModel = chatModel;
        this.storeSummaryTool = storeSummaryTool;
        this.financeDiffTool = financeDiffTool;
    }

    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                    你是营营无忧门店智能体助手，专门帮助门店管理者处理经营相关的问题。
                    
                    你可以调用的工具：
                    1. get_store_summary - 获取门店经营摘要（销售额、客流、待办等）
                    2. get_finance_diff - 获取销售凭证与金蝶凭证差异列表
                    
                    请根据用户的问题，智能地调用相应的工具来获取数据，然后给出专业的分析和建议。
                    回答要简洁明了，重点突出，便于门店管理者快速理解和决策。
                    """)
                .defaultFunctions(
                    List.of(
                        storeSummaryTool.getStoreSummary(),
                        financeDiffTool.getFinanceDiff()
                    )
                )
                .build();
    }
}
