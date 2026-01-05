package org.hotiver.sittingtimer.config;

public class ConfigDto {
    public int workSeconds;
    public int restSeconds;
    public double soundVolume;
    public boolean isMuted;

    public ConfigDto() {
    }

    public ConfigDto(int workSeconds, int restSeconds,
                     double soundVolume, boolean isMuted) {
        this.workSeconds = workSeconds;
        this.restSeconds = restSeconds;
        this.soundVolume = soundVolume;
        this.isMuted = isMuted;
    }
}
