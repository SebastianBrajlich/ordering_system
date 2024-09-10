package com.ltrlabs.client.state;

import com.ltrlabs.client.context.SystemContext;
import com.ltrlabs.client.ui.support.ConsoleColors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("initState")
@RequiredArgsConstructor
public class InitState implements SystemState {

    private final SystemContext context;

    @Override
    public void execute() {
        context.getUi().displayMessage("Welcome in Ordering System", ConsoleColors.BLUE);
        context.quit();
    }
}
