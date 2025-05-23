package ru.lytvenkovmv.loggingstarter.service;

public abstract class AbstractService<T> {
    public T doAction(T input, AbstractChain<T> chain) {
        return chain.process(input, chain);
    }
}
