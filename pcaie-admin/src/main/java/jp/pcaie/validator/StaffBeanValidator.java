package jp.pcaie.validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.StaffBean;
import jp.pcaie.mapper.MStaffMapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class StaffBeanValidator {

    public static final int MAX_NAME_LENGTH = 45;
    public static final int MAX_MAIL_LENGTH = 100;

    @Autowired
    private final MStaffMapper staffMapper = null;
    @Autowired
    private final EmailValidator emailValidator = null;
    @Autowired
    private final MessageSource messageSource = null;

    public boolean validateInputEmail(final String email,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StaffBean.email",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(email)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.put("emailError", message);
            isValid = false;
        } else if (StringUtils.length(email) > StaffBeanValidator.MAX_MAIL_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         StaffBeanValidator.MAX_MAIL_LENGTH },
                                                                 locale);
            model.put("emailError", message);
            isValid = false;
        } else if (!this.emailValidator.validate(email)) {
            final String message = this.messageSource.getMessage("validate.invalid",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.put("emailError", message);
            isValid = false;
        }

        return isValid;
    }

    public boolean validateExistsMail(final StaffBean userBean,
                                      final Map<String, Object> model,
                                      final Locale locale) {
        boolean isValid = true;
        final String mail = userBean.getEmail();
        final StaffBean existUser = this.getUserByEmail(mail);
        if (existUser != null && !existUser.getId().equals(userBean.getId())) {
            final String fieldName = this.messageSource.getMessage("StaffBean.email",
                                                                   null,
                                                                   locale);
            final String message = this.messageSource.getMessage("validate.unique",
                                                                 new String[] { fieldName },
                                                                 locale);
            model.put("emailError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateLoginUser(final StaffBean loginUser,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("validate.loginFailure",
                                                                 null,
                                                                 locale);
            model.put("error", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validatePasswordSame(final String password,
                                        final String md5Password,
                                        final String salt,
                                        final Map<String, Object> model,
                                        final Locale locale) {
        boolean isValid = true;
        final String encryptPassword = this.encryptPassword(md5Password, salt);
        if (!StringUtils.equals(password, encryptPassword)) {
            final String message = this.messageSource.getMessage("validate.loginFailure",
                                                                 null,
                                                                 locale);
            model.put("error", message);
            isValid = false;
        }
        return isValid;
    }

    private String encryptPassword(final String password, final String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password + salt) + salt);
    }

    private StaffBean getUserByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.staffMapper.fetchBean(param);
    }

    public boolean validateInputPassword(final String password,
                                         final String password2,
                                         final Map<String, Object> model,
                                         final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StaffBean.password",
                                                               null,
                                                               locale);
        final String fieldName2 = this.messageSource.getMessage("StaffBean.password2",
                                                                null,
                                                                locale);
        if (StringUtils.isBlank(password)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.put("passwordError", message);
            isValid = false;
        }
        if (StringUtils.isBlank(password2)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName2 },
                                                                 locale);
            model.put("password2Error", message);
            isValid = false;
        }
        if (!StringUtils.equals(password, password2)) {
            final String message = this.messageSource.getMessage("validate.notSame",
                                                                 new Object[] { fieldName,
                                                                         fieldName2 },
                                                                 locale);
            model.put("password2Error", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean validateInputName(final String name,
                                     final Map<String, Object> model,
                                     final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StaffBean.name",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(name)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.put("nameError", message);
            isValid = false;
        } else if (StringUtils.length(name) > StaffBeanValidator.MAX_NAME_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         StaffBeanValidator.MAX_NAME_LENGTH },
                                                                 locale);
            model.put("nameError", message);
            isValid = false;
        }
        return isValid;
    }
}
