package util.impl;

import lombok.AllArgsConstructor;

public class Exceptions {

    public static class InvalidPropertyParameterException extends RuntimeException {
        public InvalidPropertyParameterException(String message) {
            super(message);
        }
    }

    public static class InvalidRegionException extends RuntimeException {
        public InvalidRegionException(String message) {
            super(message);
        }
    }

    @AllArgsConstructor
    public static class InvalidModifierException extends RuntimeException {
        public InvalidModifierException(String message) {
            super(message);
        }
    }

    @AllArgsConstructor
    public static class NoDefaultConstructorException extends RuntimeException {
        public NoDefaultConstructorException(String message) {
            super(message);
        }
    }

}
