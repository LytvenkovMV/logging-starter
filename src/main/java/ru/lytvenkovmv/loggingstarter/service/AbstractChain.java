package ru.lytvenkovmv.loggingstarter.service;

public interface AbstractChain<T, P> {
    T process(T body, P properties, AbstractChain<T, P> chain);

    AbstractChain<T, P> init();
}
