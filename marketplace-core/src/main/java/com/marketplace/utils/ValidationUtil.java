package com.marketplace.utils;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[0-9]{7,15}$"
    );
    
    /**
     * Valide un email
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Valide un numéro de téléphone
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Valide la force d'un mot de passe
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        
        return hasUpper && hasLower && hasDigit;
    }
    
    /**
     * Nettoie une chaîne (trim + null safe)
     */
    public static String clean(String input) {
        return input != null ? input.trim() : null;
    }
    
    /**
     * Vérifie si une chaîne est vide ou null
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    /**
     * Vérifie si une chaîne n'est pas vide
     */
    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }
}