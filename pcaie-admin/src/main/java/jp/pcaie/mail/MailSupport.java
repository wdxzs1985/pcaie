package jp.pcaie.mail;

import java.util.Arrays;
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

public abstract class MailSupport {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    private String encoding;

    private String from;
    private String[] bcc;

    private String template;

    private boolean html;

    protected void send(final String[] toAddressArray,
                        final String subject,
                        final Map<String, Object> model) {
        if (ArrayUtils.isEmpty(toAddressArray)) {
            return;
        }
        final String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
                                                                        this.template,
                                                                        this.encoding,
                                                                        model);
        for (final String to : toAddressArray) {
            this.send(this.from, to, subject, text, true);
        }
    }

    protected void send(final String from,
                        final String to,
                        final String subject,
                        final String text,
                        final boolean html) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(from);
                message.setTo(to);
                message.setBcc(MailSupport.this.bcc);
                message.setSubject(subject);
                message.setText(text, html);
            }
        };
        // this.mailSender.send(preparator);
        this.log.debug("---------- Send Mail Start ----------");
        this.log.debug("From: "
                       + from);
        this.log.debug("To  : "
                       + to);
        this.log.debug("Bcc  : "
                       + Arrays.toString(this.bcc));
        this.log.debug("Sub : "
                       + subject);
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

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public boolean isHtml() {
        return this.html;
    }

    public void setHtml(final boolean html) {
        this.html = html;
    }

    public String[] getBcc() {
        return this.bcc;
    }

    public void setBcc(final String[] bcc) {
        this.bcc = bcc;
    }
}
