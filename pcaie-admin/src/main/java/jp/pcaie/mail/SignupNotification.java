package jp.pcaie.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.StaffBean;
import jp.pcaie.support.MailSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class SignupNotification {

    @Autowired
    private final MessageSource messageSource = null;

    @Autowired
    private MailSupport mailSupport = null;

    private String template;

    public void send(final StaffBean userBean, final Locale locale) {
        final String[] toAddressArray = { userBean.getEmail() };

        final String subject = this.messageSource.getMessage("user.signup.subject",
                                                             null,
                                                             locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("userBean", userBean);

        this.mailSupport.send(toAddressArray, subject, this.template, model);
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

}
