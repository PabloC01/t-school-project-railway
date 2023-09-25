package com.tsystems.pablo_canton.business.impl.debug;

import com.tsystems.pablo_canton.business.api.debug.IDebugBusinessService;
import com.tsystems.pablo_canton.persistence.api.debug.IDebugDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebugBusinessServiceImpl implements IDebugBusinessService {

    private static final String HELLO_WORLD = "2) This is HELLO WORLD from DebugBusinessService\n";


    private final IDebugDataService debugDataService;

    @Autowired
    public DebugBusinessServiceImpl(final IDebugDataService debugDataService) {
        this.debugDataService = debugDataService;
    }

    @Override
    public String getHelloWorld() {
        final var helloWorldString = debugDataService.findHelloWorld();
        return HELLO_WORLD + (helloWorldString == null ? "" : helloWorldString);
    }
}
