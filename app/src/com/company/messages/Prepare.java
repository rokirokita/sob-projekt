package com.company.messages;

import java.io.Serializable;

public class Prepare implements Message, Serializable {
    private Long id;

    public Prepare(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
