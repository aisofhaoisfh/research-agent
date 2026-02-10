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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
public class FinanceDiffTool {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FinanceDiffRequest {
        private String month; // YYYY-MM
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FinanceDiffItem {
        private String voucherNo;
        private Double salesAmount;
        private Double kingdeeAmount;
        private Double diff;
        private String status; // matched, mismatched
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FinanceDiffResponse {
        private String month;
        private List<FinanceDiffItem> items;
    }

    @Bean
    @Description("获取销售凭证与金蝶凭证差异列表。参数：month(可选)月份，格式YYYY-MM，默认当前月。")
    public FunctionCallback getFinanceDiff() {
        return FunctionCallbackWrapper.builder(new Function<FinanceDiffRequest, FinanceDiffResponse>() {
            @Override
            public FinanceDiffResponse apply(FinanceDiffRequest request) {
                // 确定月份
                String month = request.getMonth();
                if (month == null || month.isEmpty()) {
                    month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
                }
                
                // Mock 数据
                List<FinanceDiffItem> items = new ArrayList<>();
                
                // 生成一些匹配的凭证
                for (int i = 1; i <= 5; i++) {
                    double amount = 1000 + Math.random() * 5000;
                    items.add(new FinanceDiffItem(
                        "V" + month.replace("-", "") + String.format("%03d", i),
                        amount,
                        amount,
                        0.0,
                        "matched"
                    ));
                }
                
                // 生成一些不匹配的凭证
                for (int i = 6; i <= 8; i++) {
                    double salesAmount = 2000 + Math.random() * 3000;
                    double diff = Math.random() * 200 - 100; // -100 到 100 的差异
                    items.add(new FinanceDiffItem(
                        "V" + month.replace("-", "") + String.format("%03d", i),
                        salesAmount,
                        salesAmount + diff,
                        Math.abs(diff),
                        "mismatched"
                    ));
                }
                
                return new FinanceDiffResponse(month, items);
            }
        })
        .withName("get_finance_diff")
        .withDescription("获取销售凭证与金蝶凭证差异列表")
        .build();
    }
}
