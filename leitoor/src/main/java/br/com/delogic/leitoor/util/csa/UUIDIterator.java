package br.com.delogic.leitoor.util.csa;

import java.util.Iterator;
import java.util.UUID;

public class UUIDIterator implements Iterator<UUID> {

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public void remove() {

    }

}
