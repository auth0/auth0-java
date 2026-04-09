package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.CustomDomainInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CustomDomainInterceptor} path whitelisting logic.
 */
public class CustomDomainInterceptorTest {

    // --- Whitelisted paths ---

    @Test
    public void testJobsVerificationEmail() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/jobs/verification-email"));
    }

    @Test
    public void testTicketsEmailVerification() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/tickets/email-verification"));
    }

    @Test
    public void testTicketsPasswordChange() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/tickets/password-change"));
    }

    @Test
    public void testOrganizationsInvitations() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/organizations/org_123/invitations"));
    }

    @Test
    public void testOrganizationsInvitationsWithId() {
        Assertions.assertTrue(
                CustomDomainInterceptor.isWhitelisted("/api/v2/organizations/org_123/invitations/inv_456"));
    }

    @Test
    public void testUsers() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/users"));
    }

    @Test
    public void testUsersWithId() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/users/auth0|123456"));
    }

    @Test
    public void testGuardianEnrollmentsTicket() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/api/v2/guardian/enrollments/ticket"));
    }

    @Test
    public void testSelfServiceProfilesSsoTicket() {
        Assertions.assertTrue(
                CustomDomainInterceptor.isWhitelisted("/api/v2/self-service-profiles/ssp_123/sso-ticket"));
    }

    @Test
    public void testSelfServiceProfilesSsoTicketRevoke() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted(
                "/api/v2/self-service-profiles/ssp_123/sso-ticket/tkt_456/revoke"));
    }

    // --- Non-whitelisted paths ---

    @Test
    public void testClientsNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/clients"));
    }

    @Test
    public void testConnectionsNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/connections"));
    }

    @Test
    public void testCustomDomainsNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/custom-domains"));
    }

    @Test
    public void testRulesNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/rules"));
    }

    @Test
    public void testGuardianEnrollmentsWithIdNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/guardian/enrollments/enr_123"));
    }

    @Test
    public void testJobsUsersExportsNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/jobs/users-exports"));
    }

    @Test
    public void testSelfServiceProfilesListNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/self-service-profiles"));
    }

    @Test
    public void testOrganizationsWithoutInvitationsNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/organizations/org_123"));
    }

    @Test
    public void testOrganizationsMembersNotWhitelisted() {
        Assertions.assertFalse(CustomDomainInterceptor.isWhitelisted("/api/v2/organizations/org_123/members"));
    }

    // --- Paths without /api/v2 prefix (direct base URL) ---

    @Test
    public void testUsersWithoutApiPrefix() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/users"));
    }

    @Test
    public void testTicketsEmailVerificationWithoutApiPrefix() {
        Assertions.assertTrue(CustomDomainInterceptor.isWhitelisted("/tickets/email-verification"));
    }
}
