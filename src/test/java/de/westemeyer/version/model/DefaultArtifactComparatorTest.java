package de.westemeyer.version.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

class DefaultArtifactComparatorTest {
    @Test
    void sortedSet() {
        Set<Artifact> set = new TreeSet<>(new DefaultArtifactComparator());
        set.add(new Artifact("zz2", "aaa", "2.0", 0, null));
        set.add(new Artifact("aaa", "0aa", "1.0", 0, null));
        set.add(new Artifact("aaa", "0aa", "1.1", 0, null));
        set.add(new Artifact("aaa", "baa", "1.3", 0, null));
        set.add(new Artifact("0z", "0aa", "2.1", 0, null));
        ArrayList<Artifact> artifacts = new ArrayList<>(set);
        Assertions.assertEquals(5, set.size());
        Assertions.assertEquals("0z", artifacts.get(0).getGroupId());
        Assertions.assertEquals("1.0", artifacts.get(1).getVersion());
        Assertions.assertEquals("1.1", artifacts.get(2).getVersion());
        Assertions.assertEquals("1.3", artifacts.get(3).getVersion());
        Assertions.assertEquals("2.0", artifacts.get(4).getVersion());
    }
}
