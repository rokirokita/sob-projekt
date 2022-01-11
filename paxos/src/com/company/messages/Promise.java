package com.company.messages;

import java.io.Serializable;

public class Promise implements Message, Serializable {

    private Long id;
    private Long AcceptedId;
    private Long acceptedValue;

    public Promise(Long id) {
        this.id = id;
    }

    public Promise(Long id, Long acceptedId, Long acceptedValue) {
        this.id = id;
        AcceptedId = acceptedId;
        this.acceptedValue = acceptedValue;
    }

    public Long getId() {
        return id;
    }

    public Long getAcceptedId() {
        return AcceptedId;
    }

    public Long getAcceptedValue() {
        return acceptedValue;
    }

    public boolean hasAcceptedValue() {
        return acceptedValue != null;
    }
}
