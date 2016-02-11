package org.zalando.planb.revocation;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

/**
 * 
 * @author jbellmann
 *
 */
public abstract class AbstractSpringTest {

    @Rule
    public SpringMethodRule methodRule = new SpringMethodRule();

    @ClassRule
    public static final SpringClassRule clazzRule = new SpringClassRule();

}
