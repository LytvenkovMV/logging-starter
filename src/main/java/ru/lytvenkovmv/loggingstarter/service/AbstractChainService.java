package ru.lytvenkovmv.loggingstarter.service;

public class AbstractChainService {
    public String doAction(String input, AbstractChain chain) {
        return chain.next().doAction(input, chain);
    }
}
