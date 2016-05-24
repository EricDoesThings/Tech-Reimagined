package com.techreimagined.common.integrations;

public interface IIntegration {
    void preInit();

    void init();

    void postInit();
}
