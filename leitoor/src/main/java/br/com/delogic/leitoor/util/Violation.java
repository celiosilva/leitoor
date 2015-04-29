package br.com.delogic.leitoor.util;

public interface Violation {

    String getMessage();

    String name();

    public static class Of implements Violation {

        private final String name;
        private final String message;

        public Of(String name, String message) {
            this.name = name;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String name() {
            return name;
        }

    }

}
