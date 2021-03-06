package org.apereo.cas;

import org.apereo.cas.web.flow.configurer.CasMultifactorWebflowCustomizerTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * This is {@link AllTestsSuite}.
 *
 * @author Misagh Moayyed
 * @since 6.0.0-RC3
 */
@SelectClasses(CasMultifactorWebflowCustomizerTests.class)
@Suite
public class AllTestsSuite {
}
