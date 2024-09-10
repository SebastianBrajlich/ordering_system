package com.ltrlabs.client;

import com.ltrlabs.system.api.SystemAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemClient {

    private final SystemAPI api;
}
