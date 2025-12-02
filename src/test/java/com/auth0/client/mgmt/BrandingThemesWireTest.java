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
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"borders\":{\"button_border_radius\":1.1,\"button_border_weight\":1.1,\"buttons_style\":\"pill\",\"input_border_radius\":1.1,\"input_border_weight\":1.1,\"inputs_style\":\"pill\",\"show_widget_shadow\":true,\"widget_border_weight\":1.1,\"widget_corner_radius\":1.1},\"colors\":{\"base_focus_color\":\"base_focus_color\",\"base_hover_color\":\"base_hover_color\",\"body_text\":\"body_text\",\"captcha_widget_theme\":\"auto\",\"error\":\"error\",\"header\":\"header\",\"icons\":\"icons\",\"input_background\":\"input_background\",\"input_border\":\"input_border\",\"input_filled_text\":\"input_filled_text\",\"input_labels_placeholders\":\"input_labels_placeholders\",\"links_focused_components\":\"links_focused_components\",\"primary_button\":\"primary_button\",\"primary_button_label\":\"primary_button_label\",\"read_only_background\":\"read_only_background\",\"secondary_button_border\":\"secondary_button_border\",\"secondary_button_label\":\"secondary_button_label\",\"success\":\"success\",\"widget_background\":\"widget_background\",\"widget_border\":\"widget_border\"},\"displayName\":\"displayName\",\"fonts\":{\"body_text\":{\"bold\":true,\"size\":1.1},\"buttons_text\":{\"bold\":true,\"size\":1.1},\"font_url\":\"font_url\",\"input_labels\":{\"bold\":true,\"size\":1.1},\"links\":{\"bold\":true,\"size\":1.1},\"links_style\":\"normal\",\"reference_text_size\":1.1,\"subtitle\":{\"bold\":true,\"size\":1.1},\"title\":{\"bold\":true,\"size\":1.1}},\"page_background\":{\"background_color\":\"background_color\",\"background_image_url\":\"background_image_url\",\"page_layout\":\"center\"},\"themeId\":\"themeId\",\"widget\":{\"header_text_alignment\":\"center\",\"logo_height\":1.1,\"logo_position\":\"center\",\"logo_url\":\"logo_url\",\"social_buttons_layout\":\"bottom\"}}"));
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
        String expectedRequestBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
        String expectedResponseBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"base_focus_color\": \"base_focus_color\",\n"
                + "    \"base_hover_color\": \"base_hover_color\",\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"captcha_widget_theme\": \"auto\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"read_only_background\": \"read_only_background\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"displayName\": \"displayName\",\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"themeId\": \"themeId\",\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"borders\":{\"button_border_radius\":1.1,\"button_border_weight\":1.1,\"buttons_style\":\"pill\",\"input_border_radius\":1.1,\"input_border_weight\":1.1,\"inputs_style\":\"pill\",\"show_widget_shadow\":true,\"widget_border_weight\":1.1,\"widget_corner_radius\":1.1},\"colors\":{\"base_focus_color\":\"base_focus_color\",\"base_hover_color\":\"base_hover_color\",\"body_text\":\"body_text\",\"captcha_widget_theme\":\"auto\",\"error\":\"error\",\"header\":\"header\",\"icons\":\"icons\",\"input_background\":\"input_background\",\"input_border\":\"input_border\",\"input_filled_text\":\"input_filled_text\",\"input_labels_placeholders\":\"input_labels_placeholders\",\"links_focused_components\":\"links_focused_components\",\"primary_button\":\"primary_button\",\"primary_button_label\":\"primary_button_label\",\"read_only_background\":\"read_only_background\",\"secondary_button_border\":\"secondary_button_border\",\"secondary_button_label\":\"secondary_button_label\",\"success\":\"success\",\"widget_background\":\"widget_background\",\"widget_border\":\"widget_border\"},\"displayName\":\"displayName\",\"fonts\":{\"body_text\":{\"bold\":true,\"size\":1.1},\"buttons_text\":{\"bold\":true,\"size\":1.1},\"font_url\":\"font_url\",\"input_labels\":{\"bold\":true,\"size\":1.1},\"links\":{\"bold\":true,\"size\":1.1},\"links_style\":\"normal\",\"reference_text_size\":1.1,\"subtitle\":{\"bold\":true,\"size\":1.1},\"title\":{\"bold\":true,\"size\":1.1}},\"page_background\":{\"background_color\":\"background_color\",\"background_image_url\":\"background_image_url\",\"page_layout\":\"center\"},\"themeId\":\"themeId\",\"widget\":{\"header_text_alignment\":\"center\",\"logo_height\":1.1,\"logo_position\":\"center\",\"logo_url\":\"logo_url\",\"social_buttons_layout\":\"bottom\"}}"));
        GetBrandingDefaultThemeResponseContent response =
                client.branding().themes().getDefault();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"base_focus_color\": \"base_focus_color\",\n"
                + "    \"base_hover_color\": \"base_hover_color\",\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"captcha_widget_theme\": \"auto\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"read_only_background\": \"read_only_background\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"displayName\": \"displayName\",\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"themeId\": \"themeId\",\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"borders\":{\"button_border_radius\":1.1,\"button_border_weight\":1.1,\"buttons_style\":\"pill\",\"input_border_radius\":1.1,\"input_border_weight\":1.1,\"inputs_style\":\"pill\",\"show_widget_shadow\":true,\"widget_border_weight\":1.1,\"widget_corner_radius\":1.1},\"colors\":{\"base_focus_color\":\"base_focus_color\",\"base_hover_color\":\"base_hover_color\",\"body_text\":\"body_text\",\"captcha_widget_theme\":\"auto\",\"error\":\"error\",\"header\":\"header\",\"icons\":\"icons\",\"input_background\":\"input_background\",\"input_border\":\"input_border\",\"input_filled_text\":\"input_filled_text\",\"input_labels_placeholders\":\"input_labels_placeholders\",\"links_focused_components\":\"links_focused_components\",\"primary_button\":\"primary_button\",\"primary_button_label\":\"primary_button_label\",\"read_only_background\":\"read_only_background\",\"secondary_button_border\":\"secondary_button_border\",\"secondary_button_label\":\"secondary_button_label\",\"success\":\"success\",\"widget_background\":\"widget_background\",\"widget_border\":\"widget_border\"},\"displayName\":\"displayName\",\"fonts\":{\"body_text\":{\"bold\":true,\"size\":1.1},\"buttons_text\":{\"bold\":true,\"size\":1.1},\"font_url\":\"font_url\",\"input_labels\":{\"bold\":true,\"size\":1.1},\"links\":{\"bold\":true,\"size\":1.1},\"links_style\":\"normal\",\"reference_text_size\":1.1,\"subtitle\":{\"bold\":true,\"size\":1.1},\"title\":{\"bold\":true,\"size\":1.1}},\"page_background\":{\"background_color\":\"background_color\",\"background_image_url\":\"background_image_url\",\"page_layout\":\"center\"},\"themeId\":\"themeId\",\"widget\":{\"header_text_alignment\":\"center\",\"logo_height\":1.1,\"logo_position\":\"center\",\"logo_url\":\"logo_url\",\"social_buttons_layout\":\"bottom\"}}"));
        GetBrandingThemeResponseContent response = client.branding().themes().get("themeId");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"base_focus_color\": \"base_focus_color\",\n"
                + "    \"base_hover_color\": \"base_hover_color\",\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"captcha_widget_theme\": \"auto\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"read_only_background\": \"read_only_background\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"displayName\": \"displayName\",\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"themeId\": \"themeId\",\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"borders\":{\"button_border_radius\":1.1,\"button_border_weight\":1.1,\"buttons_style\":\"pill\",\"input_border_radius\":1.1,\"input_border_weight\":1.1,\"inputs_style\":\"pill\",\"show_widget_shadow\":true,\"widget_border_weight\":1.1,\"widget_corner_radius\":1.1},\"colors\":{\"base_focus_color\":\"base_focus_color\",\"base_hover_color\":\"base_hover_color\",\"body_text\":\"body_text\",\"captcha_widget_theme\":\"auto\",\"error\":\"error\",\"header\":\"header\",\"icons\":\"icons\",\"input_background\":\"input_background\",\"input_border\":\"input_border\",\"input_filled_text\":\"input_filled_text\",\"input_labels_placeholders\":\"input_labels_placeholders\",\"links_focused_components\":\"links_focused_components\",\"primary_button\":\"primary_button\",\"primary_button_label\":\"primary_button_label\",\"read_only_background\":\"read_only_background\",\"secondary_button_border\":\"secondary_button_border\",\"secondary_button_label\":\"secondary_button_label\",\"success\":\"success\",\"widget_background\":\"widget_background\",\"widget_border\":\"widget_border\"},\"displayName\":\"displayName\",\"fonts\":{\"body_text\":{\"bold\":true,\"size\":1.1},\"buttons_text\":{\"bold\":true,\"size\":1.1},\"font_url\":\"font_url\",\"input_labels\":{\"bold\":true,\"size\":1.1},\"links\":{\"bold\":true,\"size\":1.1},\"links_style\":\"normal\",\"reference_text_size\":1.1,\"subtitle\":{\"bold\":true,\"size\":1.1},\"title\":{\"bold\":true,\"size\":1.1}},\"page_background\":{\"background_color\":\"background_color\",\"background_image_url\":\"background_image_url\",\"page_layout\":\"center\"},\"themeId\":\"themeId\",\"widget\":{\"header_text_alignment\":\"center\",\"logo_height\":1.1,\"logo_position\":\"center\",\"logo_url\":\"logo_url\",\"social_buttons_layout\":\"bottom\"}}"));
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
        String expectedRequestBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
        String expectedResponseBody = ""
                + "{\n"
                + "  \"borders\": {\n"
                + "    \"button_border_radius\": 1.1,\n"
                + "    \"button_border_weight\": 1.1,\n"
                + "    \"buttons_style\": \"pill\",\n"
                + "    \"input_border_radius\": 1.1,\n"
                + "    \"input_border_weight\": 1.1,\n"
                + "    \"inputs_style\": \"pill\",\n"
                + "    \"show_widget_shadow\": true,\n"
                + "    \"widget_border_weight\": 1.1,\n"
                + "    \"widget_corner_radius\": 1.1\n"
                + "  },\n"
                + "  \"colors\": {\n"
                + "    \"base_focus_color\": \"base_focus_color\",\n"
                + "    \"base_hover_color\": \"base_hover_color\",\n"
                + "    \"body_text\": \"body_text\",\n"
                + "    \"captcha_widget_theme\": \"auto\",\n"
                + "    \"error\": \"error\",\n"
                + "    \"header\": \"header\",\n"
                + "    \"icons\": \"icons\",\n"
                + "    \"input_background\": \"input_background\",\n"
                + "    \"input_border\": \"input_border\",\n"
                + "    \"input_filled_text\": \"input_filled_text\",\n"
                + "    \"input_labels_placeholders\": \"input_labels_placeholders\",\n"
                + "    \"links_focused_components\": \"links_focused_components\",\n"
                + "    \"primary_button\": \"primary_button\",\n"
                + "    \"primary_button_label\": \"primary_button_label\",\n"
                + "    \"read_only_background\": \"read_only_background\",\n"
                + "    \"secondary_button_border\": \"secondary_button_border\",\n"
                + "    \"secondary_button_label\": \"secondary_button_label\",\n"
                + "    \"success\": \"success\",\n"
                + "    \"widget_background\": \"widget_background\",\n"
                + "    \"widget_border\": \"widget_border\"\n"
                + "  },\n"
                + "  \"displayName\": \"displayName\",\n"
                + "  \"fonts\": {\n"
                + "    \"body_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"buttons_text\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"font_url\": \"font_url\",\n"
                + "    \"input_labels\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"links_style\": \"normal\",\n"
                + "    \"reference_text_size\": 1.1,\n"
                + "    \"subtitle\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    },\n"
                + "    \"title\": {\n"
                + "      \"bold\": true,\n"
                + "      \"size\": 1.1\n"
                + "    }\n"
                + "  },\n"
                + "  \"page_background\": {\n"
                + "    \"background_color\": \"background_color\",\n"
                + "    \"background_image_url\": \"background_image_url\",\n"
                + "    \"page_layout\": \"center\"\n"
                + "  },\n"
                + "  \"themeId\": \"themeId\",\n"
                + "  \"widget\": {\n"
                + "    \"header_text_alignment\": \"center\",\n"
                + "    \"logo_height\": 1.1,\n"
                + "    \"logo_position\": \"center\",\n"
                + "    \"logo_url\": \"logo_url\",\n"
                + "    \"social_buttons_layout\": \"bottom\"\n"
                + "  }\n"
                + "}";
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
     * Compares two JsonNodes with numeric equivalence.
     */
    private boolean jsonEquals(JsonNode a, JsonNode b) {
        if (a.equals(b)) return true;
        if (a.isNumber() && b.isNumber()) return Math.abs(a.doubleValue() - b.doubleValue()) < 1e-10;
        if (a.isObject() && b.isObject()) {
            if (a.size() != b.size()) return false;
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = a.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                if (!jsonEquals(entry.getValue(), b.get(entry.getKey()))) return false;
            }
            return true;
        }
        if (a.isArray() && b.isArray()) {
            if (a.size() != b.size()) return false;
            for (int i = 0; i < a.size(); i++) {
                if (!jsonEquals(a.get(i), b.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
