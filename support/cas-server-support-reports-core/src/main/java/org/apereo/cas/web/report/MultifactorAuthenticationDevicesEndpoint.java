package org.apereo.cas.web.report;

import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.device.MultifactorAuthenticationRegisteredDevice;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.BaseCasRestActuatorEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.val;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Objects;

/**
 * This is {@link MultifactorAuthenticationDevicesEndpoint}.
 *
 * @author Misagh Moayyed
 * @since 7.1.0
 */
@Endpoint(id = "mfaDevices", enableByDefault = false)
public class MultifactorAuthenticationDevicesEndpoint extends BaseCasRestActuatorEndpoint {

    public MultifactorAuthenticationDevicesEndpoint(final CasConfigurationProperties casProperties,
                                                    final ConfigurableApplicationContext applicationContext) {
        super(casProperties, applicationContext);
    }

    /**
     * All mfa devices for user.
     *
     * @param username the username
     * @return the list
     * @throws Throwable the throwable
     */
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all present and registered MFA devices for a given user",
        parameters = @Parameter(name = "username", description = "The user that owns registered multifactor devices", in = ParameterIn.PATH))
    public List<MultifactorAuthenticationRegisteredDevice> allMfaDevicesForUser(@PathVariable final String username) throws Throwable {
        val principal = PrincipalFactoryUtils.newPrincipalFactory().createPrincipal(username);
        val providers = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(applicationContext).values();
        return providers
            .stream()
            .filter(provider -> Objects.nonNull(provider.getDeviceManager()))
            .map(provider -> provider.getDeviceManager().findRegisteredDevices(principal))
            .flatMap(List::stream)
            .toList();

    }
}
