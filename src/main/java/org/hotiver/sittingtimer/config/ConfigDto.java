package org.hotiver.sittingtimer.config;

public class ConfigDto {
    public int workSeconds;
    public int restSeconds;

    public ConfigDto() {
    }

    public ConfigDto(int workSeconds, int restSeconds) {
        this.workSeconds = workSeconds;
        this.restSeconds = restSeconds;
    }
}
