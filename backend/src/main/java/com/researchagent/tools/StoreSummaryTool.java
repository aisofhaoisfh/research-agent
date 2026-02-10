package com.researchagent.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class StoreSummaryTool {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StoreSummaryRequest {
        private String storeCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StoreSummaryResponse {
        private String storeCode;
        private String storeName;
        private Double todaySales;
        private Integer customerFlow;
        private Double avgPrice;
        private Double lossRate;
        private Integer pendingTasks;
    }

    @Bean
    @Description("获取门店经营摘要，包括销售额、客流、待办数量等。参数：storeCode(可选)门店编码，默认返回示例门店。")
    public FunctionCallback getStoreSummary() {
        return FunctionCallbackWrapper.builder(new Function<StoreSummaryRequest, StoreSummaryResponse>() {
            @Override
            public StoreSummaryResponse apply(StoreSummaryRequest request) {
                // Mock 数据
                String storeCode = request.getStoreCode() != null ? request.getStoreCode() : "A01032";
                String storeName = "菜湖店";
                
                // 简单随机数据（实际可替换为真实数据源）
                double todaySales = 12000 + Math.random() * 2000;
                int customerFlow = 300 + (int)(Math.random() * 100);
                double avgPrice = 35 + Math.random() * 5;
                double lossRate = 1.5 + Math.random() * 1.0;
                int pendingTasks = 20 + (int)(Math.random() * 10);
                
                return new StoreSummaryResponse(
                    storeCode,
                    storeName,
                    Math.round(todaySales * 100.0) / 100.0,
                    customerFlow,
                    Math.round(avgPrice * 100.0) / 100.0,
                    Math.round(lossRate * 100.0) / 100.0,
                    pendingTasks
                );
            }
        })
        .withName("get_store_summary")
        .withDescription("获取门店经营摘要，包括销售额、客流、待办数量等")
        .build();
    }
}
