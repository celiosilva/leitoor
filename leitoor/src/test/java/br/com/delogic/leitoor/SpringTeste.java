package br.com.delogic.leitoor;

import javax.inject.Inject;

import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.delogic.csa.manager.EmailManager;
import br.com.delogic.csa.manager.email.EmailAddress;
import br.com.delogic.csa.manager.email.EmailContent;
import br.com.delogic.leitoor.util.Log4jManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
                "classpath:spring/application-beans.xml",
                "classpath:spring/environment-beans.xml",
                "classpath:spring/web-beans.xml"})
public class SpringTeste extends Assert {

    static {
        System.setProperty("spring.profiles.active", "TEST");
        Log4jManager.setLevel(Level.INFO);
    }

    @Inject
    protected ApplicationContext ctx;

    @Inject
    private EmailManager         emailManager;

    @Test
    public void testSpring() {
        assertNotNull(ctx);
    }

    @Test
    @Ignore
    public void email() {
        emailManager.send(
            new EmailAddress("Leitoor", "leitoor@delogic.com.br"),
            new EmailContent("Teste", "conteúdo do teste"),
            new EmailAddress("Célio", "celio@delogic.com.br"));
    }

}
