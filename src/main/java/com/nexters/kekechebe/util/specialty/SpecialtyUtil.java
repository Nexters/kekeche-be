package com.nexters.kekechebe.util.specialty;

import java.util.ArrayList;
import java.util.List;

public final class SpecialtyUtil {
    private SpecialtyUtil() {}

    public static List<Long> getIdsToDeleteSpecialties(List<Long> beforeIds, List<Long> afterIds) {
        List<Long> result = new ArrayList<>(beforeIds);
        result.removeAll(afterIds);
        return result;
    }

    public static List<Long> getIdsToCreateSpecialties(List<Long> beforeIds, List<Long> afterIds) {
        List<Long> result = new ArrayList<>(afterIds);
        result.removeAll(beforeIds);
        return result;
    }
}
