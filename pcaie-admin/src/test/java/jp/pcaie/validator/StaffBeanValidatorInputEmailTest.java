package jp.pcaie.validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.pcaie.Application;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StaffBeanValidatorInputEmailTest {

    @Autowired
    private StaffBeanValidator staffBeanValidator = null;
    @Autowired
    private final MessageSource messageSource = null;

    private final static Locale LOCALE = Locale.getDefault();

    private final static String ERROR_ATTR = "emailError";
    private final static String ERROR_BLANK = "メールアドレスを入力してください。";
    private final static String ERROR_TOOLONG = "メールアドレスが100文字以下にしてください。";
    private final static String ERROR_INVALID = "無効なメールアドレスです。もう一度やり直してください。";

    @Test
    public void testNormal() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("test@pcaie.jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertTrue(result);
        Assert.assertNull(model.get(ERROR_ATTR));
    }

    @Test
    public void testBlank() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("",
                                                                          model,
                                                                          LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_BLANK);
    }

    @Test
    public void testTooLong() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final String email = StringUtils.repeat("a",
                                                StaffBeanValidator.MAX_MAIL_LENGTH) + "@pcaie.jp";
        final boolean result = this.staffBeanValidator.validateInputEmail(email,
                                                                          model,
                                                                          LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_TOOLONG);
    }

    @Test
    public void testNormalWithDash() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("t-test@pcaie.jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertTrue(result);
        Assert.assertNull(model.get(ERROR_ATTR));
    }

    @Test
    public void testNormalWithDot() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("t.test@pcaie.jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertTrue(result);
        Assert.assertNull(model.get(ERROR_ATTR));
    }

    @Test
    public void testNormalWithDotBefor() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("t.test@pcaie.co.jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertTrue(result);
        Assert.assertNull(model.get(ERROR_ATTR));
    }

    @Test
    public void testNormalWithoutAt() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("t.test.pcaie.co.jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_INVALID);
    }

    @Test
    public void testNormalWithDoubleDot() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputEmail("t.test@pcaie.co..jp",
                                                                          model,
                                                                          LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_INVALID);
    }

}
