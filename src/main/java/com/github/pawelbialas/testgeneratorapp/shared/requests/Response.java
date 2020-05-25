package com.github.pawelbialas.testgeneratorapp.shared.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pawelbialas.testgeneratorapp.shared.domain.dto.BaseItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Response implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("payload")
    private BaseItem payload;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate;

    public Response (String message, BaseItem payload) {
        super();
        this.message = message;
        this.payload = payload;
        this.createdDate = OffsetDateTime.now();
    }


}
