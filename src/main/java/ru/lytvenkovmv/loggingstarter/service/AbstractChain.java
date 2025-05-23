package ru.lytvenkovmv.loggingstarter.service;

public interface AbstractChain<T> {
    T process(T body, AbstractChain<T> chain);
}
