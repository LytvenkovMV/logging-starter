package ru.lytvenkovmv.loggingstarter.context;

public class BodyLoggingContext {
    private final ThreadLocal<String> context = new ThreadLocal<>();

    public void set(String body) {
        context.set(body);
    }

    public String get() {
        return context.get();
    }

    public void clear() {
        context.remove();
    }
}
