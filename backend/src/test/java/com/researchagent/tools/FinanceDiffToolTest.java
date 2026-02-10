package com.researchagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FinanceDiffToolTest {

    @Autowired
    private FinanceDiffTool financeDiffTool;

    @Test
    void testGetFinanceDiff() {
        FunctionCallback tool = financeDiffTool.getFinanceDiff();
        assertNotNull(tool);
        assertEquals("get_finance_diff", tool.getName());
    }

    @Test
    void testFinanceDiffRequest() {
        FinanceDiffTool.FinanceDiffRequest request = new FinanceDiffTool.FinanceDiffRequest();
        request.setMonth("2025-01");
        
        assertEquals("2025-01", request.getMonth());
    }

    @Test
    void testFinanceDiffItem() {
        FinanceDiffTool.FinanceDiffItem item = new FinanceDiffTool.FinanceDiffItem(
            "V202501001",
            1250.00,
            1250.00,
            0.00,
            "matched"
        );
        
        assertEquals("V202501001", item.getVoucherNo());
        assertEquals(1250.00, item.getSalesAmount());
        assertEquals(1250.00, item.getKingdeeAmount());
        assertEquals(0.00, item.getDiff());
        assertEquals("matched", item.getStatus());
    }
}
