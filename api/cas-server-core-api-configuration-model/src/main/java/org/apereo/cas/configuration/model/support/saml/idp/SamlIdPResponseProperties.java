package org.apereo.cas.configuration.model.support.saml.idp;

import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

import com.google.common.base.Splitter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is {@link SamlIdPResponseProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@RequiresModule(name = "cas-server-support-saml-idp")
@Getter
@Setter
@Accessors(chain = true)
public class SamlIdPResponseProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 7200477683583467619L;

    /**
     * Indicate the encoding type of the credential used when rendering the saml response.
     */
    private SignatureCredentialTypes credentialType = SignatureCredentialTypes.X509;

    /**
     * Time unit in seconds used to skew authentication dates such
     * as valid-from and valid-until elements.
     */
    @DurationCapable
    private String skewAllowance = "PT30S";

    /**
     * Whether error responses should be signed.
     */
    private boolean signError;

    /**
     * Indicates the default name-format for all attributes
     * in case the individual attribute is not individually mapped.
     */
    private String defaultAttributeNameFormat = "uri";

    /**
     * Each individual attribute can be mapped to a particular name-format.
     * Example: {@code attributeName->basic|uri|unspecified|custom-format-etc,...}.
     */
    private List<String> attributeNameFormats = new ArrayList<>();

    /**
     * Configure attribute name formats and build a map.
     *
     * @return the map
     */
    public Map<String, String> configureAttributeNameFormats() {
        if (this.attributeNameFormats.isEmpty()) {
            return new HashMap<>();
        }
        val nameFormats = new HashMap<String, String>();
        this.attributeNameFormats
            .forEach(value -> Arrays.stream(value.split(","))
                .forEach(format -> {
                    val values = Splitter.on("->").splitToList(format);

                    if (values.size() == 2) {
                        nameFormats.put(values.getFirst(), values.get(1));
                    }
                }));
        return nameFormats;
    }

    /**
     * Indicate the type of encoding used when rendering the
     * saml response and its signature blog.
     */
    public enum SignatureCredentialTypes {

        /**
         * DER-Encoded format.
         */
        BASIC,
        /**
         * PEM-encoded X509 format.
         */
        X509
    }
}
