package com.smarth.solutions.core.api.model.cache;

import java.io.Serializable;

public record SubscriptionCache (
    Long id
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
