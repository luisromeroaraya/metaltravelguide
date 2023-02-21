package com.metaltravelguide.places.models.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4908799533201852065L;
    private LocalDateTime receivedAt;
    private HttpMethod method;
    private int status;
    private String path;
    private String message;
    private Map<String, Object> infos;

    public ErrorDTO addInfo(String key, Object value) {
        infos.put(key, value);
        return this;
    }
}
