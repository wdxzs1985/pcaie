package jp.pcaie.admin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.admin.domain.UserBean;
import jp.pcaie.admin.domain.UserPasswordHistoryBean;
import jp.pcaie.admin.mail.ForgotNotification;
import jp.pcaie.admin.mail.PasswordChangedNotification;
import jp.pcaie.admin.mail.SignupNotification;
import jp.pcaie.admin.mapper.MUsersMapper;
import jp.pcaie.admin.mapper.RUserPasswordHistoryMapper;
import jp.pcaie.admin.validator.EmailValidator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class UserService {

    @Autowired
    private final MUsersMapper usersMapper = null;
    @Autowired
    private final RUserPasswordHistoryMapper userPasswordHistoryMapper = null;
    @Autowired
    private final EmailValidator emailValidator = null;
    @Autowired
    private final MessageSource messageSource = null;
    @Autowired
    private final ForgotNotification forgotNotification = null;
    @Autowired
    private final SignupNotification signupNotification = null;
    @Autowired
    private final PasswordChangedNotification passwordChangedNotification = null;

    public UserBean doLogin(final String email, final String password, final String salt, final Model model, final Locale locale) {
        if (this.validateInputEmail(email, model, locale)) {
            final UserBean loginUser = this.getUserBeanByEmail(email);
            if (this.validateLoginUser(loginUser, model, locale)) {
                final String md5Password = loginUser.getPassword();
                if (this.validatePasswordSame(password,
                                              md5Password,
                                              salt,
                                              model,
                                              locale)) {
                    return loginUser;
                }
            }
        }
        return null;
    }

    public UserBean doLogin(final String token) {
        final UserBean loginUser = this.getUserBeanByToken(token);
        return loginUser;
    }

    private boolean validateInputEmail(final String email, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.email",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(email)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (StringUtils.length(email) > 100) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         100 },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (!this.emailValidator.validate(email)) {
            final String message = this.messageSource.getMessage("validate.unavailable",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateLoginUser(final UserBean loginUser, final Model model, final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("validate.loginFailure",
                                                                 null,
                                                                 locale);
            model.addAttribute("error", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validatePasswordSame(final String password, final String md5Password, final String salt, final Model model, final Locale locale) {
        boolean isValid = true;
        final String encryptPassword = this.encryptPassword(md5Password, salt);
        if (!StringUtils.equals(password, encryptPassword)) {
            final String message = this.messageSource.getMessage("validate.loginFailure",
                                                                 null,
                                                                 locale);
            model.addAttribute("error", message);
            isValid = false;
        }
        return isValid;
    }

    private String encryptPassword(final String password, final String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password + salt) + salt);
    }

    public boolean sendForgotMail(final String email, final Model model, final Locale locale) {
        boolean isSuccess = false;
        if (this.validateInputEmail(email, model, locale)) {
            final UserBean userBean = this.getUserBeanByEmail(email);
            if (this.validateUserExists(userBean, model, locale)) {
                this.usersMapper.deleteToken(userBean);
                final UserPasswordHistoryBean userPasswordHistoryBean = this.generatePasswordHistoryBean(userBean,
                                                                                                         24);
                if (this.userPasswordHistoryMapper.insert(userPasswordHistoryBean) > 0) {
                    this.forgotNotification.send(userPasswordHistoryBean,
                                                 locale);
                    isSuccess = true;
                }
            }
        }
        return isSuccess;
    }

    private UserBean getUserBeanByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        final UserBean userBean = this.usersMapper.fetchBean(param);
        return userBean;
    }

    private boolean validateUserExists(final UserBean loginUser, final Model model, final Locale locale) {
        boolean isValid = true;
        if (loginUser == null) {
            final String message = this.messageSource.getMessage("validate.userNotExists",
                                                                 null,
                                                                 locale);
            model.addAttribute("error", message);
            isValid = false;
        }
        return isValid;
    }

    private UserPasswordHistoryBean generatePasswordHistoryBean(final UserBean userBean, final int expireHours) {
        final String token = this.generateToken(userBean);
        final UserPasswordHistoryBean passwordHistoryBean = new UserPasswordHistoryBean();
        passwordHistoryBean.setUserBean(userBean);
        passwordHistoryBean.setToken(token);

        Date expire = new Date();
        expire = DateUtils.addHours(expire, expireHours);
        passwordHistoryBean.setExpire(expire);
        return passwordHistoryBean;
    }

    private String generateToken(final UserBean userBean) {
        final String salt = RandomStringUtils.randomAlphanumeric(4);
        final String email = this.encryptPlainText(userBean.getEmail(), salt);
        final String password = this.encryptPlainText(userBean.getPassword(),
                                                      salt);
        final String timestamp = this.encryptPlainText(DateFormatUtils.format(System.currentTimeMillis(),
                                                                              "yyyyMMdd"),
                                                       salt);
        final String token = this.encryptPlainText(email + password + timestamp,
                                                   salt);
        return token;
    }

    private String encryptPlainText(final String text, final String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(text) + salt) + salt);
    }

    public boolean findPasswordHistory(final String token, final Model model, final Locale locale) {
        boolean isSuccess = false;
        if (this.validateInputToken(token, model, locale)) {
            final UserBean userBean = this.getUserBeanByToken(token);
            if (this.validateUserPasswordHistory(userBean, model, locale)) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private boolean validateInputToken(final String token, final Model model, final Locale locale) {
        boolean isValid = true;
        if (StringUtils.isBlank(token) || StringUtils.length(token) != 32) {
            final String fieldName = this.messageSource.getMessage("user.forgot.changePassword.token",
                                                                   null,
                                                                   locale);
            final String message = this.messageSource.getMessage("validate.unavailable",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("error", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateUserPasswordHistory(final UserBean userBean, final Model model, final Locale locale) {
        boolean isValid = true;
        if (userBean == null) {
            final String fieldName = this.messageSource.getMessage("user.forgot.changePassword.token",
                                                                   null,
                                                                   locale);
            final String message = this.messageSource.getMessage("validate.unavailable",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("error", message);
            isValid = false;
        }
        return isValid;
    }

    private UserBean getUserBeanByToken(final String token) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("token", token);
        final UserBean userBean = this.usersMapper.fetchBeanByToken(param);
        return userBean;
    }

    public boolean changePassword(final String token, final String password, final Model model, final Locale locale) {
        boolean isSuccess = false;
        if (this.validateInputPassword(password, model, locale)) {
            final UserBean userBean = this.getUserBeanByToken(token);
            if (this.validateUserPasswordHistory(userBean, model, locale)) {
                userBean.setPassword(DigestUtils.md5Hex(password));
                this.usersMapper.update(userBean);
                this.usersMapper.deleteToken(userBean);
                this.passwordChangedNotification.send(userBean, locale);
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    public boolean doSignup(final String email, final String password, final String nickname, final Model model, final Locale locale) {
        boolean isSuccess = false;
        if (this.validateSignup(email, password, nickname, model, locale)) {
            final UserBean userBean = new UserBean();
            userBean.setEmail(email);
            userBean.setPassword(DigestUtils.md5Hex(password));
            userBean.setNickname(nickname);
            if (this.usersMapper.insert(userBean) > 0) {
                this.signupNotification.send(userBean, locale);
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    private boolean validateSignup(final String email, final String password, final String nickname, final Model model, final Locale locale) {
        boolean isValid = true;
        isValid = this.validateInputEmail(email, model, locale) && isValid;
        if (isValid) {
            final UserBean userBean = this.getUserBeanByEmail(email);
            if (userBean != null) {
                final String message = this.messageSource.getMessage("validate.userExists",
                                                                     null,
                                                                     locale);
                model.addAttribute("emailError", message);
                isValid = false;
            }
        }
        isValid = this.validateInputPassword(password, model, locale) && isValid;
        isValid = this.validateInputNickname(nickname, model, locale) && isValid;
        return isValid;
    }

    private boolean validateInputPassword(final String password, final Model model, final Locale locale) {
        boolean isValid = true;
        if (StringUtils.isBlank(password)) {
            final String fieldName = this.messageSource.getMessage("UserBean.password",
                                                                   null,
                                                                   locale);
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("passwordError", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateInputNickname(final String nickname, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("UserBean.nickname",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(nickname)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("nicknameError", message);
            isValid = false;
        } else if (StringUtils.length(nickname) > 45) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         45 },
                                                                 locale);
            model.addAttribute("nicknameError", message);
            isValid = false;
        }
        return isValid;
    }

    public String postLogin(final UserBean userBean) {
        this.usersMapper.deleteToken(userBean);
        final UserPasswordHistoryBean userPasswordHistoryBean = this.generatePasswordHistoryBean(userBean,
                                                                                                 24 * 7);
        if (this.userPasswordHistoryMapper.insert(userPasswordHistoryBean) > 0) {
            return userPasswordHistoryBean.getToken();
        }
        return null;
    }

    public void logout(final UserBean userBean) {
        this.usersMapper.deleteToken(userBean);
    }
}
