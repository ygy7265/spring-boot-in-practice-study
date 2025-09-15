package org.example.springbootinpractice.ch01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private final CustomProperties customProperties;

    @Autowired
    public AppService(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }
    public CustomProperties getCustomProperties() {
        return this.customProperties;
    }
}
