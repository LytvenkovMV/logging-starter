package ru.lytvenkovmv.loggingstarter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;

import java.util.List;

@Service
public class RequestBodyChain implements AbstractChain<String> {
    @Autowired
    private List<AbstractService<String>> serviceList;
    private final ThreadLocal<Integer> iterator;

    public RequestBodyChain() {
        this.iterator = ThreadLocal.withInitial(() -> 0);
    }

    @Override
    public String process(String input, AbstractChain<String> chain) {
        int curr = iterator.get();

        if (curr < serviceList.size()) {
            iterator.set(curr + 1);

            return serviceList.get(curr).doAction(input, chain);
        }

        return input;
    }
}
