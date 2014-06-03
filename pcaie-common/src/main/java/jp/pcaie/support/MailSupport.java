package jp.pcaie.support;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String encoding;

    private String from;

    private String bcc;

    private boolean html;

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
            this.send(this.from, to, this.bcc, subject, text, true);
        }
    }

    public void send(final String from,
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
        // this.mailSender.send(preparator);
        this.log.debug("---------- Send Mail Start ----------");
        this.log.debug("From : " + from);
        this.log.debug("To   : " + to);
        this.log.debug("Bcc  : " + bcc);
        this.log.debug("Sub  : " + subject);
        this.log.debug("Text : ");
        this.log.debug(text);
        this.log.debug("----------  Send Mail End  ----------");
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public boolean isHtml() {
        return this.html;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public String getBcc() {
        return this.bcc;
    }

    public void setBcc(final String bcc) {
        this.bcc = bcc;
    }

    public JavaMailSender getMailSender() {
        return this.mailSender;
    }

    public void setMailSender(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }

    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
