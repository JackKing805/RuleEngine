package com.cool.jerry.v3

class ProxyMutableList<E>(
    originList: MutableList<E>,
    private val set:(old:E,new:E)->Unit
) : MutableList<E> {
    private val proxyList = mutableListOf<E>()
    private val mOriginList = mutableListOf<E>()

    init {
        mOriginList.addAll(originList)
        proxyList.addAll(originList)
    }

    override val size: Int = proxyList.size

    override fun contains(element: E): Boolean {
        return proxyList.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return proxyList.containsAll(elements)
    }

    override fun add(element: E): Boolean {
        return proxyList.add(element)
    }

    override fun add(index: Int, element: E) {
        return proxyList.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        return proxyList.addAll(index, elements)
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return proxyList.addAll(elements)
    }

    override fun clear() {
        proxyList.clear()
    }

    override fun get(index: Int): E {
        return proxyList[index]
    }

    override fun isEmpty(): Boolean {
        return proxyList.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return proxyList.iterator()
    }

    override fun listIterator(): MutableListIterator<E> {
        return proxyList.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return proxyList.listIterator(index)
    }

    override fun removeAt(index: Int): E {
        return proxyList.removeAt(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        return proxyList.subList(fromIndex, toIndex)
    }

    override fun set(index: Int, element: E): E {
        try {
            mOriginList[index]?.let{
                set(it,element)
            }
        }catch (_:Exception){}
        return proxyList.set(index, element)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return proxyList.retainAll(elements)
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return proxyList.removeAll(elements)
    }

    override fun remove(element: E): Boolean {
        return proxyList.remove(element)
    }

    override fun lastIndexOf(element: E): Int {
        return proxyList.lastIndexOf(element)
    }

    override fun indexOf(element: E): Int {
        return proxyList.indexOf(element)
    }
}