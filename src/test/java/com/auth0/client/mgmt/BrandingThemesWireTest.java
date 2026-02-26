package com.auth0.client.mgmt;

import com.auth0.client.mgmt.branding.types.CreateBrandingThemeRequestContent;
import com.auth0.client.mgmt.branding.types.UpdateBrandingThemeRequestContent;
import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.types.BrandingThemeBorders;
import com.auth0.client.mgmt.types.BrandingThemeBordersButtonsStyleEnum;
import com.auth0.client.mgmt.types.BrandingThemeBordersInputsStyleEnum;
import com.auth0.client.mgmt.types.BrandingThemeColors;
import com.auth0.client.mgmt.types.BrandingThemeFontBodyText;
import com.auth0.client.mgmt.types.BrandingThemeFontButtonsText;
import com.auth0.client.mgmt.types.BrandingThemeFontInputLabels;
import com.auth0.client.mgmt.types.BrandingThemeFontLinks;
import com.auth0.client.mgmt.types.BrandingThemeFontLinksStyleEnum;
import com.auth0.client.mgmt.types.BrandingThemeFontSubtitle;
import com.auth0.client.mgmt.types.BrandingThemeFontTitle;
import com.auth0.client.mgmt.types.BrandingThemeFonts;
import com.auth0.client.mgmt.types.BrandingThemePageBackground;
import com.auth0.client.mgmt.types.BrandingThemePageBackgroundPageLayoutEnum;
import com.auth0.client.mgmt.types.BrandingThemeWidget;
import com.auth0.client.mgmt.types.BrandingThemeWidgetHeaderTextAlignmentEnum;
import com.auth0.client.mgmt.types.BrandingThemeWidgetLogoPositionEnum;
import com.auth0.client.mgmt.types.BrandingThemeWidgetSocialButtonsLayoutEnum;
import com.auth0.client.mgmt.types.CreateBrandingThemeResponseContent;
import com.auth0.client.mgmt.types.GetBrandingDefaultThemeResponseContent;
import com.auth0.client.mgmt.types.GetBrandingThemeResponseContent;
import com.auth0.client.mgmt.types.UpdateBrandingThemeResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrandingThemesWireTest {
    private MockWebServer server;
    private ManagementApi client;
    private ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testCreate() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testCreate_response.json")));
        CreateBrandingThemeResponseContent response = client.branding()
                .themes()
                .create(CreateBrandingThemeRequestContent.builder()
                        .borders(BrandingThemeBorders.builder()
                                .buttonBorderRadius(1.1)
                                .buttonBorderWeight(1.1)
                                .buttonsStyle(BrandingThemeBordersButtonsStyleEnum.PILL)
                                .inputBorderRadius(1.1)
                                .inputBorderWeight(1.1)
                                .inputsStyle(BrandingThemeBordersInputsStyleEnum.PILL)
                                .showWidgetShadow(true)
                                .widgetBorderWeight(1.1)
                                .widgetCornerRadius(1.1)
                                .build())
                        .colors(BrandingThemeColors.builder()
                                .bodyText("body_text")
                                .error("error")
                                .header("header")
                                .icons("icons")
                                .inputBackground("input_background")
                                .inputBorder("input_border")
                                .inputFilledText("input_filled_text")
                                .inputLabelsPlaceholders("input_labels_placeholders")
                                .linksFocusedComponents("links_focused_components")
                                .primaryButton("primary_button")
                                .primaryButtonLabel("primary_button_label")
                                .secondaryButtonBorder("secondary_button_border")
                                .secondaryButtonLabel("secondary_button_label")
                                .success("success")
                                .widgetBackground("widget_background")
                                .widgetBorder("widget_border")
                                .build())
                        .fonts(BrandingThemeFonts.builder()
                                .bodyText(BrandingThemeFontBodyText.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .buttonsText(BrandingThemeFontButtonsText.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .fontUrl("font_url")
                                .inputLabels(BrandingThemeFontInputLabels.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .links(BrandingThemeFontLinks.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .linksStyle(BrandingThemeFontLinksStyleEnum.NORMAL)
                                .referenceTextSize(1.1)
                                .subtitle(BrandingThemeFontSubtitle.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .title(BrandingThemeFontTitle.builder()
                                        .bold(true)
                                        .size(1.1)
                                        .build())
                                .build())
                        .pageBackground(BrandingThemePageBackground.builder()
                                .backgroundColor("background_color")
                                .backgroundImageUrl("background_image_url")
                                .pageLayout(BrandingThemePageBackgroundPageLayoutEnum.CENTER)
                                .build())
                        .widget(BrandingThemeWidget.builder()
                                .headerTextAlignment(BrandingThemeWidgetHeaderTextAlignmentEnum.CENTER)
                                .logoHeight(1.1)
                                .logoPosition(BrandingThemeWidgetLogoPositionEnum.CENTER)
                                .logoUrl("logo_url")
                                .socialButtonsLayout(BrandingThemeWidgetSocialButtonsLayoutEnum.BOTTOM)
                                .build())
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testCreate_request.json");
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertTrue(jsonEquals(expectedJson, actualJson), "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testCreate_response.json");
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGetDefault() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(
                        TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testGetDefault_response.json")));
        GetBrandingDefaultThemeResponseContent response =
                client.branding().themes().getDefault();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testGetDefault_response.json");
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testGet() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testGet_response.json")));
        GetBrandingThemeResponseContent response = client.branding().themes().get("themeId");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testGet_response.json");
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testDelete() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.branding().themes().delete("themeId");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testUpdate() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testUpdate_response.json")));
        UpdateBrandingThemeResponseContent response = client.branding()
                .themes()
                .update(
                        "themeId",
                        UpdateBrandingThemeRequestContent.builder()
                                .borders(BrandingThemeBorders.builder()
                                        .buttonBorderRadius(1.1)
                                        .buttonBorderWeight(1.1)
                                        .buttonsStyle(BrandingThemeBordersButtonsStyleEnum.PILL)
                                        .inputBorderRadius(1.1)
                                        .inputBorderWeight(1.1)
                                        .inputsStyle(BrandingThemeBordersInputsStyleEnum.PILL)
                                        .showWidgetShadow(true)
                                        .widgetBorderWeight(1.1)
                                        .widgetCornerRadius(1.1)
                                        .build())
                                .colors(BrandingThemeColors.builder()
                                        .bodyText("body_text")
                                        .error("error")
                                        .header("header")
                                        .icons("icons")
                                        .inputBackground("input_background")
                                        .inputBorder("input_border")
                                        .inputFilledText("input_filled_text")
                                        .inputLabelsPlaceholders("input_labels_placeholders")
                                        .linksFocusedComponents("links_focused_components")
                                        .primaryButton("primary_button")
                                        .primaryButtonLabel("primary_button_label")
                                        .secondaryButtonBorder("secondary_button_border")
                                        .secondaryButtonLabel("secondary_button_label")
                                        .success("success")
                                        .widgetBackground("widget_background")
                                        .widgetBorder("widget_border")
                                        .build())
                                .fonts(BrandingThemeFonts.builder()
                                        .bodyText(BrandingThemeFontBodyText.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .buttonsText(BrandingThemeFontButtonsText.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .fontUrl("font_url")
                                        .inputLabels(BrandingThemeFontInputLabels.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .links(BrandingThemeFontLinks.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .linksStyle(BrandingThemeFontLinksStyleEnum.NORMAL)
                                        .referenceTextSize(1.1)
                                        .subtitle(BrandingThemeFontSubtitle.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .title(BrandingThemeFontTitle.builder()
                                                .bold(true)
                                                .size(1.1)
                                                .build())
                                        .build())
                                .pageBackground(BrandingThemePageBackground.builder()
                                        .backgroundColor("background_color")
                                        .backgroundImageUrl("background_image_url")
                                        .pageLayout(BrandingThemePageBackgroundPageLayoutEnum.CENTER)
                                        .build())
                                .widget(BrandingThemeWidget.builder()
                                        .headerTextAlignment(BrandingThemeWidgetHeaderTextAlignmentEnum.CENTER)
                                        .logoHeight(1.1)
                                        .logoPosition(BrandingThemeWidgetLogoPositionEnum.CENTER)
                                        .logoUrl("logo_url")
                                        .socialButtonsLayout(BrandingThemeWidgetSocialButtonsLayoutEnum.BOTTOM)
                                        .build())
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testUpdate_request.json");
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertTrue(jsonEquals(expectedJson, actualJson), "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody =
                TestResources.loadResource("/wire-tests/BrandingThemesWireTest_testUpdate_response.json");
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    /**
     * Compares two JsonNodes with numeric equivalence and null safety.
     * For objects, checks that all fields in 'expected' exist in 'actual' with matching values.
     * Allows 'actual' to have extra fields (e.g., default values added during serialization).
     */
    private boolean jsonEquals(JsonNode expected, JsonNode actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (expected.equals(actual)) return true;
        if (expected.isNumber() && actual.isNumber())
            return Math.abs(expected.doubleValue() - actual.doubleValue()) < 1e-10;
        if (expected.isObject() && actual.isObject()) {
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = expected.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                JsonNode actualValue = actual.get(entry.getKey());
                if (actualValue == null || !jsonEquals(entry.getValue(), actualValue)) return false;
            }
            return true;
        }
        if (expected.isArray() && actual.isArray()) {
            if (expected.size() != actual.size()) return false;
            for (int i = 0; i < expected.size(); i++) {
                if (!jsonEquals(expected.get(i), actual.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
