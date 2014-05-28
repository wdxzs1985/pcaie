package jp.pcaie.admin.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.admin.domain.UserBean;
import jp.pcaie.admin.domain.UserPasswordHistoryBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ForgotNotification extends MailSupport {

    @Autowired
    private final MessageSource messageSource = null;

    public void send(final UserPasswordHistoryBean userPasswordHistoryBean, final Locale locale) {
        final UserBean userBean = userPasswordHistoryBean.getUserBean();
        final String[] toAddressArray = { userBean.getEmail() };

        final String subject = this.messageSource.getMessage("user.forgot.changePassword.subject",
                                                             null,
                                                             locale);

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("userPasswordHistoryBean", userPasswordHistoryBean);

        this.send(toAddressArray, subject, model);
    }

}
