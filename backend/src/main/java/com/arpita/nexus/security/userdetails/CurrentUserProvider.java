
package com.arpita.nexus.security.userdetails;

import com.arpita.nexus.user.entity.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProvider {

    public User getCurrentUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
            || !authentication.isAuthenticated()
            || !(authentication.getPrincipal()
            instanceof CustomUserDetails userDetails)) {
            throw new AuthenticationCredentialsNotFoundException(
                "Authenticated user not found."
            );
        }

        return userDetails.getUser();
    }

    public UUID getCurrentOrganizationId() {
        UUID organizationId = getCurrentUser()
            .getOrganization()
            .getId();

        if (organizationId == null) {
            throw new IllegalStateException(
                "Authenticated user has no organization."
            );
        }

        return organizationId;
    }
}
