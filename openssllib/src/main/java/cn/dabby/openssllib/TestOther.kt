package cn.dabby.openssllib

/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/12/29
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
class TestOther {
    companion object {
        init {
            System.loadLibrary("testOther")
        }
    }
    external fun d()
}