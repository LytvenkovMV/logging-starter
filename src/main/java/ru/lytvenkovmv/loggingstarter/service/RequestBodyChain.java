package ru.lytvenkovmv.loggingstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestBodyChain implements AbstractChain {
    @Autowired
    private List<AbstractChainService> serviceList;
    ThreadLocal<Integer> iterator = new ThreadLocal<>();

    @Override
    public AbstractChainService next() {
        int index = iterator.get();
        iterator.set(index + 1);

        return serviceList.get(index);
    }
}
