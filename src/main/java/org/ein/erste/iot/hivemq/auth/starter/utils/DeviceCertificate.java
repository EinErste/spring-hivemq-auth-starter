package org.ein.erste.iot.hivemq.auth.starter.utils;

import java.util.Date;

public record DeviceCertificate(byte[] certificate, Date validTill) {
}
