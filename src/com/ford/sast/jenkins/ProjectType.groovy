package com.ford.sast.jenkins

enum ProjectType {
    
    WebApp('WebApp', 100001),
    WebService('WebService', 100002),
    Mobile('Mobile', 100003)

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
