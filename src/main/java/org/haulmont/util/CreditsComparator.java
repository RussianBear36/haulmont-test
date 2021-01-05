package org.haulmont.util;

import org.haulmont.dao.Credit;

import java.util.Comparator;

public class CreditsComparator implements Comparator<Credit> {
    @Override
    public int compare(Credit o1, Credit o2) {
        return o1.getBank().getName().compareTo(o2.getBank().getName());
    }
}
