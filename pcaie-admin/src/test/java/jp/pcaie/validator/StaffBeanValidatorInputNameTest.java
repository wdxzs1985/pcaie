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
public class StaffBeanValidatorInputNameTest {

    @Autowired
    private StaffBeanValidator staffBeanValidator = null;
    @Autowired
    private final MessageSource messageSource = null;

    private final static Locale LOCALE = Locale.getDefault();

    private final static String ERROR_ATTR = "nameError";
    private final static String ERROR_BLANK = "名前を入力してください。";
    private final static String ERROR_TOOLONG = "名前が45文字以下にしてください。";

    @Test
    public void testNormal() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputName("名前",
                                                                         model,
                                                                         LOCALE);
        Assert.assertTrue(result);
        Assert.assertNull(model.get(ERROR_ATTR));
    }

    @Test
    public void testBlank() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final boolean result = this.staffBeanValidator.validateInputName("",
                                                                         model,
                                                                         LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_BLANK);
    }

    @Test
    public void testTooLong() {
        final Map<String, Object> model = new HashMap<String, Object>();
        final String email = StringUtils.repeat("名",
                                                StaffBeanValidator.MAX_NAME_LENGTH) + "前";
        final boolean result = this.staffBeanValidator.validateInputName(email,
                                                                         model,
                                                                         LOCALE);
        Assert.assertFalse(result);
        Assert.assertNotNull(model.get(ERROR_ATTR));
        Assert.assertEquals(model.get(ERROR_ATTR), ERROR_TOOLONG);
    }

}
