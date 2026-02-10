package com.researchagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreSummaryToolTest {

    @Autowired
    private StoreSummaryTool storeSummaryTool;

    @Test
    void testGetStoreSummary() {
        FunctionCallback tool = storeSummaryTool.getStoreSummary();
        assertNotNull(tool);
        assertEquals("get_store_summary", tool.getName());
    }

    @Test
    void testStoreSummaryRequest() {
        StoreSummaryTool.StoreSummaryRequest request = new StoreSummaryTool.StoreSummaryRequest();
        request.setStoreCode("A01032");
        
        assertEquals("A01032", request.getStoreCode());
    }

    @Test
    void testStoreSummaryResponse() {
        StoreSummaryTool.StoreSummaryResponse response = new StoreSummaryTool.StoreSummaryResponse(
            "A01032",
            "菜湖店",
            12580.00,
            342,
            36.8,
            2.1,
            23
        );
        
        assertEquals("A01032", response.getStoreCode());
        assertEquals("菜湖店", response.getStoreName());
        assertEquals(12580.00, response.getTodaySales());
        assertEquals(342, response.getCustomerFlow());
        assertEquals(36.8, response.getAvgPrice());
        assertEquals(2.1, response.getLossRate());
        assertEquals(23, response.getPendingTasks());
    }
}
