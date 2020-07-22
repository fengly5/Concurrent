package com.frode.Concurrent.excutor.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class SafeWM {
    class WMRange {
        final int upper;
        final int lower;

        WMRange(int upper, int lower) {
            this.upper = upper;
            this.lower = lower;
        }
    }

    final AtomicReference<WMRange> rf =
            new AtomicReference<>(new WMRange(0, 0));

    // 设置库存上限
    void setUpper(int v) {
        WMRange nr;
        WMRange or;
        do {
            or = rf.get();//自旋的时候需要不断的获取新的值
            // 检查参数合法性
            if (v < or.lower) {
                throw new IllegalArgumentException();
            }
            nr = new WMRange(v, or.lower);
        } while (!rf.compareAndSet(or, nr));
    }
}
