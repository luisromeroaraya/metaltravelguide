package com.metaltravelguide.places.models.dtos;

import java.io.Serial;
import java.io.Serializable;

public class TokenDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6024681481309972503L;
    public String token;

    public TokenDTO(String token) { this.token = token; }
}
