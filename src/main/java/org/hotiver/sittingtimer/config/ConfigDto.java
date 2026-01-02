package org.hotiver.sittingtimer.config;

public class ConfigDto {
    public int workSeconds;
    public int restSeconds;
    public double soundVolume;

    public ConfigDto() {
    }

    public ConfigDto(int workSeconds, int restSeconds,
                     double soundVolume) {
        this.workSeconds = workSeconds;
        this.restSeconds = restSeconds;
        this.soundVolume = soundVolume;
    }
}
