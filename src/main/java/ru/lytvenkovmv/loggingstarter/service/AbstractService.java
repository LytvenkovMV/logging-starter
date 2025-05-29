package ru.lytvenkovmv.loggingstarter.service;

public abstract class AbstractService<T, P> {
    public T doAction(T input, P properties, AbstractChain<T, P> chain) {
        return chain.process(input, properties, chain);
    }
}
