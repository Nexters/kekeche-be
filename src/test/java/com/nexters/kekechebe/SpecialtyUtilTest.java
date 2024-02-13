package com.nexters.kekechebe;

import com.nexters.kekechebe.util.specialty.SpecialtyUtil;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecialtyUtilTest {
    @Test
    public void 수정_전_주특기와_수정_후_주특기에서_삭제할_주특기_찾기() {
        List<Long> idsToDeleteSpecialties = SpecialtyUtil.getIdsToDeleteSpecialties(List.of(1L, 2L, 3L, 4L), List.of(2L, 3L));
        assertThat(idsToDeleteSpecialties)
                .isEqualTo(List.of(1L, 4L));
    }

    @Test
    public void 수정_전_주특기와_수정_후_주특기에서_삭제할_주특기가_없는_경우() {
        List<Long> idsToDeleteSpecialties = SpecialtyUtil.getIdsToDeleteSpecialties(List.of(1L, 2L), List.of(1L, 2L, 3L));
        assertThat(idsToDeleteSpecialties)
                .isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    public void 수정_전_주특기와_수정_후_주특기에서_추가할_주특기_찾기() {
        List<Long> idsToDeleteSpecialties = SpecialtyUtil.getIdsToCreateSpecialties(List.of(2L, 4L), List.of(1L, 2L));
        assertThat(idsToDeleteSpecialties)
                .isEqualTo(List.of(1L));
    }

    @Test
    public void 수정_전_주특기와_수정_후_주특기에서_추가할_주특기가_없는_경우() {
        List<Long> idsToDeleteSpecialties = SpecialtyUtil.getIdsToCreateSpecialties(List.of(1L, 2L), List.of(2L));
        assertThat(idsToDeleteSpecialties)
                .isEqualTo(Collections.EMPTY_LIST);
    }
}
