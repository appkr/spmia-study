package dev.appkr.licenses.controllers;

import dev.appkr.licenses.services.LicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LicenseServiceControllerTestTest {

    private MockMvc mvc;

    @Test
    public void getLicense() throws Exception {
        mvc.perform(get("/v1/organizations/{organizationId}/licenses/{licenseId}",
                UUID.randomUUID(), UUID.randomUUID()))
            .andExpect(status().isOk());
    }

    @BeforeEach
    public void setup() {
        final LicenseServiceController controller = new LicenseServiceController(new LicenseService());
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
}