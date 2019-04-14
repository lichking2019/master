package com.wt.master.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slave")
public class DynamicDataSourceProperties {
    private static final boolean DEFAULT_ENABLE = false;

    private boolean enable = DEFAULT_ENABLE;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
