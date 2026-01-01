package org.hotiver.sittingtimer.config;

public class Config {
    public int workSeconds;
    public int restSeconds;

    public Config() {
    }

    public Config(int workSeconds, int restSeconds) {
        this.workSeconds = workSeconds;
        this.restSeconds = restSeconds;
    }
}
