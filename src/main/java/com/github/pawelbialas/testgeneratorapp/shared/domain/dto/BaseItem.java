package com.github.pawelbialas.testgeneratorapp.shared.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseItem {


    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("version")
    private Long version = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("lastModified")
    private OffsetDateTime lastModifiedDate;

//    public int hashCode () {
//        return Objects.hash(this.id);
//    }
//
//    public boolean equals (Object that) {
//        return this == that || that instanceof BaseItem && Objects.equals(this.id, ((BaseItem) that).id);
//    }

}
