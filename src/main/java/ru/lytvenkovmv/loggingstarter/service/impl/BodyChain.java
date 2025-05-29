package ru.lytvenkovmv.loggingstarter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;

import java.util.List;

@Service
public class BodyChain implements AbstractChain<String, AbstractLogBodyProperties> {
    @Autowired
    private List<AbstractService<String, AbstractLogBodyProperties>> serviceList;
    private final ThreadLocal<Integer> iterator = ThreadLocal.withInitial(() -> 0);

    @Override
    public String process(String input, AbstractLogBodyProperties properties, AbstractChain<String, AbstractLogBodyProperties> chain) {
        int curr = iterator.get();

        if (curr < serviceList.size()) {
            iterator.set(curr + 1);

            return serviceList.get(curr).doAction(input, properties, chain);
        }
        iterator.set(0);

        return input;
    }

    public AbstractChain<String, AbstractLogBodyProperties> init() {
        iterator.set(0);

        return this;
    }
}
