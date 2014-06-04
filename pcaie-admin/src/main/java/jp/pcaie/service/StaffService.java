package jp.pcaie.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.domain.StaffBean;
import jp.pcaie.mapper.MStaffMapper;
import jp.pcaie.support.Paginate;
import jp.pcaie.validator.StaffBeanValidator;

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

    @Autowired
    private final MStaffMapper staffMapper = null;
    @Autowired
    private final StaffBeanValidator staffBeanValidator = null;
    @Autowired
    private final MessageSource messageSource = null;

    public StaffBean doLogin(final String mail,
                             final String password,
                             final String salt,
                             final Model model,
                             final Locale locale) {
        if (this.staffBeanValidator.validateInputEmail(mail, model, locale)) {
            final StaffBean loginUser = this.getUserByEmail(mail);
            if (this.staffBeanValidator.validateLoginUser(loginUser,
                                                          model,
                                                          locale)) {
                final String md5Password = loginUser.getPassword();
                if (this.staffBeanValidator.validatePasswordSame(password,
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

    private StaffBean getUserByEmail(final String email) {
        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("email", email);
        return this.staffMapper.fetchBean(param);
    }

    public boolean updatePassword(final StaffBean userBean,
                                  final String password,
                                  final String password2,
                                  final Model model,
                                  final Locale locale) {
        final boolean isValid = this.staffBeanValidator.validateInputPassword(password,
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

    public boolean updateUser(final StaffBean staffBean,
                              final Model model,
                              final Locale locale) {
        final String mail = staffBean.getEmail();
        boolean isMailValid = this.staffBeanValidator.validateInputEmail(mail,
                                                                         model,
                                                                         locale);
        if (isMailValid) {
            isMailValid = this.staffBeanValidator.validateExistsMail(staffBean,
                                                                     model,
                                                                     locale);
        }

        final String name = staffBean.getName();
        final boolean isNameValid = this.staffBeanValidator.validateInputName(name,
                                                                              model,
                                                                              locale);

        final String password = staffBean.getPassword();
        final String password2 = staffBean.getPassword2();
        boolean isPasswordValid = true;
        if (StringUtils.isNotBlank(password) || StringUtils.isNotBlank(password2)) {
            isPasswordValid = this.staffBeanValidator.validateInputPassword(password,
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

    public boolean createUser(final StaffBean staffBean,
                              final Model model,
                              final Locale locale) {
        boolean isMailValid = this.staffBeanValidator.validateInputEmail(staffBean.getEmail(),
                                                                         model,
                                                                         locale);
        if (isMailValid) {
            isMailValid = this.staffBeanValidator.validateExistsMail(staffBean,
                                                                     model,
                                                                     locale);
        }
        final boolean isNameValid = this.staffBeanValidator.validateInputName(staffBean.getName(),
                                                                              model,
                                                                              locale);
        final boolean isPasswordValid = this.staffBeanValidator.validateInputPassword(staffBean.getPassword(),
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
