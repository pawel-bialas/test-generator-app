package com.github.pawelbialas.testgeneratorapp.shared;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false)
    private UUID id;


    @Version
    private Long version;

    public int hashCode () {
        return Objects.hash(this.id);
    }

    public boolean equals (Object that) {
        return this == that || that instanceof BaseEntity && Objects.equals(this.id, ((BaseEntity) that).id);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
