package com.uma.vulnesolve.models;

public class Union {
    private int source;
    private int target;

    public Union() {
        this.source = 0;
        this.target = 0;
    }

    public Union(int source, int target) {
        this.source = source;
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return
        "Union: {" +
            "origen: '" + source + "', "+
            "destino: '" + target + "'"+
        '}';
    }
}
