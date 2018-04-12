package com.auth0.json.mgmt;

public class EmailTemplate {

    private String template;
    private String body;
    private String from;
    private String resultUrl;
    private String subject;
    private String syntax;
    private int urlLifetimeInSeconds;
    private boolean enabled;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public int getUrlLifetimeInSeconds() {
        return urlLifetimeInSeconds;
    }

    public void setUrlLifetimeInSeconds(int urlLifetimeInSeconds) {
        this.urlLifetimeInSeconds = urlLifetimeInSeconds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
