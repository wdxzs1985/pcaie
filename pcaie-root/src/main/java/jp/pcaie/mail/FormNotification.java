package jp.pcaie.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.FormBean;
import jp.pcaie.support.MailSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class FormNotification extends MailSupport {

    @Autowired
    private final MessageSource messageSource = null;

    public void send(final FormBean formBean, final Locale locale) {

        final String[] toAddressArray = { formBean.getCustomerBean().getEmail() };

        final String subject = this.messageSource.getMessage("form.mail.subject",
                                                             null,
                                                             locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("formBean", formBean);

        this.send(toAddressArray, subject, model);
    }

}
