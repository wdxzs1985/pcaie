package jp.pcaie.mail;

import java.util.Collections;
import java.util.Map;

import jp.pcaie.domain.FormBean;
import jp.pcaie.support.MailSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormMail {

    @Autowired
    private MailSupport mailSupport = null;

    private String subject;

    private String template;

    public void send(final FormBean formBean) {

        final String[] toAddressArray = new String[] { formBean.getEmail() };
        final Map<String, Object> model = Collections.singletonMap("formBean",
                                                                   formBean);
        this.mailSupport.send(toAddressArray,
                              this.subject,
                              this.template,
                              model);

    }
}
