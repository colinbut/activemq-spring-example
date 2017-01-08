/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.mycompany.activemq;

import java.util.Arrays;
import java.util.Objects;

public class Email {

    private String toRecipient;
    private String[] ccRecipients;
    private String[] bccRecipients;
    private String subject;
    private String emailBody;

    public String getToRecipient() {
        return toRecipient;
    }

    public void setToRecipient(String toRecipient) {
        this.toRecipient = toRecipient;
    }

    public String[] getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(String[] ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public String[] getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(String[] bccRecipients) {
        this.bccRecipients = bccRecipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(toRecipient, email.toRecipient) &&
            Arrays.equals(ccRecipients, email.ccRecipients) &&
            Arrays.equals(bccRecipients, email.bccRecipients) &&
            Objects.equals(subject, email.subject) &&
            Objects.equals(emailBody, email.emailBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toRecipient, ccRecipients, bccRecipients, subject, emailBody);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Email{");
        sb.append("toRecipient='").append(toRecipient).append('\'');
        sb.append(", ccRecipients=").append(Arrays.toString(ccRecipients));
        sb.append(", bccRecipients=").append(Arrays.toString(bccRecipients));
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", emailBody='").append(emailBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
