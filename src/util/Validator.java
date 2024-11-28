package util;

public class Validator {
    public static boolean validateEyeColor(String eyeColor) {
        return eyeColor.matches("[a-zA-Z]+");
    }

    public static boolean validateAge(int age) {
        return age >= 0 && age <= 120;
    }

    public static boolean validateGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female");
    }

    public static boolean validateVolume(double volume) {
        return volume > 0;
    }
}
