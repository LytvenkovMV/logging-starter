package ru.lytvenkovmv.loggingstarter.properties;

import java.util.List;

public abstract class AbstractLogBodyProperties {
    public abstract List<String> getMaskedFields();

    public abstract Integer getBodyMaxLength();
}
