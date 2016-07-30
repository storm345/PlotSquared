package com.intellectualcrafters.plot.object;

import java.util.function.Consumer;

public abstract class RunnableVal<T> implements Runnable, Consumer<T> {

    public T value;
    
    public RunnableVal() {}
    
    public RunnableVal(T value) {
        this.value = value;
    }

    @Override
    public void run() {
        run(this.value);
    }
    
    public abstract void run(T value);

    @Override
    public final void accept(T t) {
        this.value = t;
        this.run();
    }
}
