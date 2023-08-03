package org.ycframework.context;


public class YcBeanDefinition {
    private boolean islazy;
    private String scope="singleton";
    private boolean isPrimary;

    public boolean isIslazy() {
        return islazy;
    }

    public void setIslazy(boolean islazy) {
        this.islazy = islazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getClassInfo() {

        return null;
    }
}
