package org.apereo.cas.support.oauth.events;

import org.apereo.cas.support.events.AbstractCasEvent;
import lombok.Getter;
import org.apereo.inspektr.common.web.ClientInfo;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * This is {@link OAuth20AccessTokenRequestEvent}.
 *
 * @author Misagh Moayyed
 * @since 7.3.0
 */
public class OAuth20AccessTokenRequestEvent extends AbstractCasEvent {
    @Serial
    private static final long serialVersionUID = -5859884708612488910L;

    @Getter
    private final Map context;

    public OAuth20AccessTokenRequestEvent(final Object source, final ClientInfo clientInfo,
                                          final Map context) {
        super(source, clientInfo);
        this.context = new HashMap<>(context);
    }
}
