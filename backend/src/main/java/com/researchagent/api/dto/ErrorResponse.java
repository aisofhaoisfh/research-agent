package com.researchagent.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String code;
    private String message;
    private Object details;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"code\":\"").append(escape(code)).append("\"");
        sb.append(",\"message\":\"").append(escape(message)).append("\"");
        if (details != null) {
            sb.append(",\"details\":").append(details.toString());
        }
        sb.append("}");
        return sb.toString();
    }

    private String escape(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }
}
