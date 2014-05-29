package jp.pcaie.admin.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.admin.domain.StaffBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class PasswordChangedNotification extends MailSupport {

    @Autowired
    private final MessageSource messageSource = null;

    public void send(final StaffBean userBean, final Locale locale) {
        final String[] toAddressArray = { userBean.getEmail() };

        final String subject = this.messageSource.getMessage("user.passwordChanged.subject",
                                                             null,
                                                             locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("userBean", userBean);

        this.send(toAddressArray, subject, model);
    }

}