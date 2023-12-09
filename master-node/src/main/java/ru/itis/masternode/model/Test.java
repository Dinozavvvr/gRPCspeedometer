package ru.itis.masternode.model;

import ru.itis.masternode.api.dto.TestConfig;

public class Test {

    public TestState testState;

    public Test(TestConfig testConfig) {
        this.testState = new TestState();
    }

}
