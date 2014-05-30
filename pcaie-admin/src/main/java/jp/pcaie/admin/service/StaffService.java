package jp.pcaie.admin.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.admin.domain.StaffBean;
import jp.pcaie.admin.mapper.MStaffMapper;
import jp.pcaie.admin.validator.EmailValidator;
import jp.pcaie.common.Paginate;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Transactional
public class StaffService {

    public static final int MAX_MAIL_LENGTH = 100;
    public static final int MAX_NAME_LENGTH = 100;

    @Autowired
    private final MStaffMapper staffMapper = null;
    @Autowired
    private final EmailValidator emailValidator = null;
    @Autowired
    private final MessageSource messageSource = null;

    public StaffBean doLogin(final String mail, final String password, final String salt, final Model model, final Locale locale) {
        if (this.validateInputEmail(mail, model, locale)) {
            final StaffBean loginUser = this.getUserByEmail(mail);
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

    private boolean validateInputEmail(final String email, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StaffBean.email",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(email)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        } else if (StringUtils.length(email) > MAX_MAIL_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_MAIL_LENGTH },
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

    private boolean validateExistsMail(final StaffBean userBean, final Model model, final Locale locale) {
        boolean isValid = true;
        final String mail = userBean.getEmail();
        final StaffBean existUser = this.getUserByEmail(mail);
        if (existUser != null && existUser.getId() != userBean.getId()) {
            final String message = this.messageSource.getMessage("validate.userExists",
                                                                 null,
                                                                 locale);
            model.addAttribute("emailError", message);
            isValid = false;
        }
        return isValid;
    }

    private boolean validateLoginUser(final StaffBean loginUser, final Model model, final Locale locale) {
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

    private StaffBean getUserByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.staffMapper.fetchBean(param);
    }

    private boolean validateInputPassword(final String password, final String password2, final Model model, final Locale locale) {
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
            model.addAttribute("passwordError", message);
            isValid = false;
        }
        if (StringUtils.isBlank(password2)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName2 },
                                                                 locale);
            model.addAttribute("password2Error", message);
            isValid = false;
        }
        if (!StringUtils.equals(password, password2)) {
            final String message = this.messageSource.getMessage("validate.notSame",
                                                                 new Object[] { fieldName,
                                                                         fieldName2 },
                                                                 locale);
            model.addAttribute("password2Error", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean updatePassword(final StaffBean userBean, final String password, final String password2, final Model model, final Locale locale) {
        final boolean isValid = this.validateInputPassword(password,
                                                           password2,
                                                           model,
                                                           locale);
        if (isValid) {
            userBean.setPassword(DigestUtils.md5Hex(password));
            this.staffMapper.update(userBean);
        }
        return isValid;
    }

    public void doSearch(final Paginate<StaffBean> paginate) {
        final Map<String, Object> params = paginate.getParams();
        final int itemCount = this.staffMapper.count(params);

        paginate.setItemCount(itemCount);
        paginate.compute();

        List<StaffBean> items = null;
        if (itemCount == 0) {
            items = Collections.emptyList();
        } else {
            items = this.staffMapper.fetchList(params);
        }
        paginate.setItems(items);
    }

    public StaffBean getUserById(final Integer id) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return this.staffMapper.fetchBean(param);
    }

    public boolean updateUser(final StaffBean staffBean, final Model model, final Locale locale) {
        final String mail = staffBean.getEmail();
        boolean isMailValid = this.validateInputEmail(mail, model, locale);
        if (isMailValid) {
            isMailValid = this.validateExistsMail(staffBean, model, locale);
        }

        final String name = staffBean.getName();
        final boolean isNameValid = this.validateInputName(name, model, locale);

        final String password = staffBean.getPassword();
        final String password2 = staffBean.getPassword2();
        boolean isPasswordValid = true;
        if (StringUtils.isNotBlank(password) || StringUtils.isNotBlank(password2)) {
            isPasswordValid = this.validateInputPassword(password,
                                                         password2,
                                                         model,
                                                         locale);
        }
        final boolean isValid = isMailValid && isNameValid && isPasswordValid;
        if (isValid) {
            if (StringUtils.isNotBlank(password)) {
                staffBean.setPassword(DigestUtils.md5Hex(password));
            }
            this.staffMapper.update(staffBean);
        }
        return isValid;
    }

    private boolean validateInputName(final String name, final Model model, final Locale locale) {
        boolean isValid = true;
        final String fieldName = this.messageSource.getMessage("StaffBean.name",
                                                               null,
                                                               locale);
        if (StringUtils.isBlank(name)) {
            final String message = this.messageSource.getMessage("validate.empty",
                                                                 new Object[] { fieldName },
                                                                 locale);
            model.addAttribute("nameError", message);
            isValid = false;
        } else if (StringUtils.length(name) > MAX_NAME_LENGTH) {
            final String message = this.messageSource.getMessage("validate.tooLong",
                                                                 new Object[] { fieldName,
                                                                         MAX_NAME_LENGTH },
                                                                 locale);
            model.addAttribute("nameError", message);
            isValid = false;
        }
        return isValid;
    }

    public boolean createUser(final StaffBean staffBean, final Model model, final Locale locale) {
        boolean isMailValid = this.validateInputEmail(staffBean.getEmail(),
                                                      model,
                                                      locale);
        if (isMailValid) {
            isMailValid = this.validateExistsMail(staffBean, model, locale);
        }
        final boolean isNameValid = this.validateInputName(staffBean.getName(),
                                                           model,
                                                           locale);
        final boolean isPasswordValid = this.validateInputPassword(staffBean.getPassword(),
                                                                   staffBean.getPassword2(),
                                                                   model,
                                                                   locale);
        final boolean isValid = isMailValid && isNameValid && isPasswordValid;
        if (isValid) {
            final String password = staffBean.getPassword();
            staffBean.setPassword(DigestUtils.md5Hex(password));
            this.staffMapper.insert(staffBean);
        }
        return isValid;
    }

    public void delete(final StaffBean staffBean) {
        this.staffMapper.delete(staffBean);
    }
}
