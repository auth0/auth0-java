package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.emailtemplates.EmailTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EmailTemplateTest extends JsonTest<EmailTemplate> {

    private static final String json = "{\"template\": \"welcome_email\", \"from\": \"you@auth0.com\", \"subject\": \"Some subject\", \"syntax\": \"liquid\", \"body\": \"<html> </html>\", \"enabled\": true}";

    @Test
    public void shouldSerialize() throws Exception {
        EmailTemplate template = new EmailTemplate();
        template.setResultUrl("https://auth0.com");
        template.setUrlLifetimeInSeconds(993311);
        template.setBody("SOME HTML");
        template.setEnabled(false);
        template.setSyntax("html");
        template.setSubject("Hello world");
        template.setFrom("me@auth0.com");
        template.setName("farewell");

        String serialized = toJSON(template);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("resultUrl", "https://auth0.com"));
        assertThat(serialized, JsonMatcher.hasEntry("urlLifetimeInSeconds", 993311));
        assertThat(serialized, JsonMatcher.hasEntry("body", "SOME HTML"));
        assertThat(serialized, JsonMatcher.hasEntry("enabled", false));
        assertThat(serialized, JsonMatcher.hasEntry("syntax", "html"));
        assertThat(serialized, JsonMatcher.hasEntry("subject", "Hello world"));
        assertThat(serialized, JsonMatcher.hasEntry("from", "me@auth0.com"));
        assertThat(serialized, JsonMatcher.hasEntry("template", "farewell"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        EmailTemplate template = fromJSON(json, EmailTemplate.class);

        assertThat(template, is(notNullValue()));
        assertThat(template.getName(), is("welcome_email"));
        assertThat(template.getFrom(), is("you@auth0.com"));
        assertThat(template.getSubject(), is("Some subject"));
        assertThat(template.getSyntax(), is("liquid"));
        assertThat(template.getBody(), is("<html> </html>"));
        assertThat(template.isEnabled(), is(true));
    }

    @Test
    public void shouldHaveDefaults() {
        EmailTemplate template = new EmailTemplate();
        assertThat(template.getSyntax(), is("liquid"));
    }
}
