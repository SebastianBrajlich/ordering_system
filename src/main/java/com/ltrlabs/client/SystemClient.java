package com.ltrlabs.client;

import com.ltrlabs.client.context.SystemContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemClient {

    private final SystemContext systemContext;

    public void start() {
        systemContext.execute();
    }

}
