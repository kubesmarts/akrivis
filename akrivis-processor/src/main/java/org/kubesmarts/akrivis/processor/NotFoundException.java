package org.kubesmarts.akrivis.processor;

import org.kie.yard.api.model.Input;

import java.util.List;

public class NotFoundException extends Throwable {

    public NotFoundException(String notFound) {
        super("Not found "+ notFound);
    }
}

