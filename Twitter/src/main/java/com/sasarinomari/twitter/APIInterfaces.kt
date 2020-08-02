package com.sasarinomari.twitter

interface BaseInterface {
    fun onStart()
}
interface ErrorInterface {
    fun onUncaughtError()
    fun onNetworkError(retrySelf: ()->Unit)
}
interface IterableInterface : BaseInterface, ErrorInterface {
    fun onFinished()
    fun onIterate(listIndex: Int)
    fun onRateLimit(listIndex: Int)
}
interface FetchListInterface : BaseInterface, ErrorInterface {
    fun onFinished(list: ArrayList<*>)
    fun onFetch(listSize: Int)
    fun onRateLimit(listSize: Int)
}
interface FetchObjectInterface : BaseInterface, ErrorInterface {
    fun onFinished(obj: Any)
    fun onRateLimit()
}
interface FoundObjectInterface : BaseInterface, ErrorInterface {
    fun onFinished(obj: Any)
    fun onRateLimit()
    fun onNotFound()
}
interface PostInterface : BaseInterface, ErrorInterface{
    fun onFinished(obj: Any)
    fun onRateLimit()
}