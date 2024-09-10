package com.ltrlabs.client.context;

import com.ltrlabs.client.state.SystemState;
import com.ltrlabs.client.ui.UI;
import com.ltrlabs.system.api.SystemAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class SystemContext {

    private final SystemAPI api;
    private final UI ui;
    private SystemState state;
    private boolean run = true;

    @Autowired
    public void changeState(@Qualifier("initState") SystemState state) {
        this.state = state;
    }

    public void execute() {
        while (run) {
            state.execute();
        }
    }

    public void quit() {
        this.run = false;
    }
}
