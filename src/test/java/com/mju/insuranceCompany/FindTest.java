package com.mju.insuranceCompany;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

class FindTest {


    @Test
    void findTest () throws Exception{
        //given
        Tester k24 = new Tester("Kim", 24);
        Tester k25 = new Tester("Kim", 25);
        Tester l40 = new Tester("Lee", 40);
        Tester l41 = new Tester("Lee", 41);

        ArrayList<Tester> tt = new ArrayList<>();
        tt.add(k24);
        tt.add(l40);
        //when

        int i = tt.indexOf(k25);
        int l = tt.indexOf(l41);
        boolean cl = tt.contains(l41);
        //then
        Assertions.assertThat(i).isSameAs(0);
        Assertions.assertThat(l).isSameAs(1);
        Assertions.assertThat(cl).isSameAs(true);


    }


    static class Tester{
        private String name;
        private int age;

        public Tester(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tester tester = (Tester) o;
            return Objects.equals(name, tester.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
