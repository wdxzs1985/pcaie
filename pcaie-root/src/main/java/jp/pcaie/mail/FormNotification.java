package jp.pcaie.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.FormBean;
import jp.pcaie.support.MailSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class FormNotification {

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private MailSupport mailSupport = null;

    private String template;

    public void send(final FormBean formBean, final Locale locale) {
        final String[] toAddressArray = { formBean.getCustomerBean().getEmail() };

        final String subject = this.messageSource.getMessage("form.mail.subject",
                                                             null,
                                                             locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("formBean", formBean);

        this.mailSupport.send(toAddressArray, subject, this.template, model);
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

}
