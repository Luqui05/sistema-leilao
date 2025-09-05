package com.lucas.slbackend.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/** Utility for password strength validation and recovery code generation. */
public final class PasswordUtil {
  private static final String CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // removed easily confused chars
  private static final SecureRandom RNG = new SecureRandom();
  private static final Pattern STRONG_PWD = Pattern.compile(
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6,}$");

  private PasswordUtil() {}

  /** Generates an 8-character uppercase alphanumeric code for password recovery. */
  public static String generateRecoveryCode() {
    StringBuilder sb = new StringBuilder(8);
    for (int i = 0; i < 8; i++) {
      sb.append(CODE_CHARS.charAt(RNG.nextInt(CODE_CHARS.length())));
    }
    return sb.toString();
  }

  public static boolean isStrongPassword(String pwd) {
    return pwd != null && STRONG_PWD.matcher(pwd).matches();
  }
}
