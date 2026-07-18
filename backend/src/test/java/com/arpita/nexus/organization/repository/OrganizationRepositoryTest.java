package com.arpita.nexus.organization.repository;

import com.arpita.nexus.organization.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository repository;

    @Test
    void shouldSaveOrganization() {

        Organization organization = new Organization();

        organization.setId(UUID.randomUUID());
        organization.setName("OpenAI");
        organization.setCode("OPENAI");
        organization.setEmail("contact@openai.com");

        Organization saved = repository.save(organization);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.existsByCode("OPENAI")).isTrue();
        assertThat(repository.existsByEmail("contact@openai.com")).isTrue();
    }
}