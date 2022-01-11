package com.company.messages;

import java.io.Serializable;

public class Accept implements Message, Serializable {
    private Long id;
    private Long value;

    public Accept(Long id, Long value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getValue() {
        return value;
    }
}
