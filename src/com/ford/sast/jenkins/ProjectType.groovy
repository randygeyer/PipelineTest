package com.ford.sast.jenkins

enum ProjectType {
    
    WebApp('WebApp', 100004),
    WebService('WebService', 100005),
    Mobile('Mobile', 100006)

    private static final String PREFIX = 'Ford - '

    public ProjectType(String ps, int id) {
        this.preset = PREFIX + ps
        this.presetId = id
    }

    private final int presetId;
    int getPresetId() {
        return presetId
    }
    
    private final String preset;
    String getPreset() {
        this.preset
    }
}
