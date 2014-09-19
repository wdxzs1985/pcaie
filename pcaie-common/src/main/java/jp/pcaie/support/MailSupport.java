package jp.pcaie.support;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailSupport {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${mail.encoding:UTF-8}")
    private String encoding;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.bcc}")
    private String bcc;

    public void send(final String[] toAddressArray,
                     final String subject,
                     final String template,
                     final Map<String, Object> model) {
        if (ArrayUtils.isEmpty(toAddressArray)) {
            return;
        }
        final String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
                                                                        template,
                                                                        this.encoding,
                                                                        model);
        for (final String to : toAddressArray) {
            this.internalSend(this.getFrom(),
                              to,
                              this.bcc,
                              subject,
                              text,
                              false);
        }
    }

    protected void internalSend(final String from,
                                final String to,
                                final String bcc,
                                final String subject,
                                final String text,
                                final boolean html) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(from);
                message.setTo(to);
                message.setBcc(bcc);
                message.setSubject(subject);
                message.setText(text, html);
            }
        };
        this.log.debug("---------- Send Mail Start ----------");
        this.log.debug("From : " + from);
        this.log.debug("To   : " + to);
        this.log.debug("Bcc  : " + bcc);
        this.log.debug("Sub  : " + subject);
        this.log.debug("Text : ");
        this.log.debug(text);
        this.mailSender.send(preparator);
        this.log.debug("----------  Send Mail End  ----------");
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

}
