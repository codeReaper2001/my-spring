package com.demo.springframework.test.forCircleDepend;

public class Wife {

    private Husband husband;
    private IMother mother;

    public String queryHusband() {
        return "Wife.husband、Mother.callMother：" + mother.callMother();
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public void setMother(IMother mother) {
        this.mother = mother;
    }

    public Husband getHusband() {
        return husband;
    }

    public IMother getMother() {
        return mother;
    }
}
