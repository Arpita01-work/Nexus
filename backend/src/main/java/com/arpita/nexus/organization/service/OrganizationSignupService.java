
package com.arpita.nexus.organization.service;

import com.arpita.nexus.authentication.dto.OrganizationSignupRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;

public interface OrganizationSignupService {

    OrganizationResponse signup(OrganizationSignupRequest request);
}
