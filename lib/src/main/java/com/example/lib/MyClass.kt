package com.example.lib

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.exceptions.Exceptions
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
class MyClass {
}

open class Person()

class Student : Person {

    constructor(name: String) {

    }

}

fun main() {
    //throttleWithTimeout操作符
    //源发射数据时，如果两次数据的发射间隔小于指定时间，就会丢弃前一次的数据,直到指定时间内都没有新数据发射时才进行发射
    Observable.create<Int> { subscriber ->
        subscriber.onNext(1)
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            throw Exceptions.propagate(e)
        }
        subscriber.onNext(2)
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            throw Exceptions.propagate(e)
        }
        subscriber.onNext(3)
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            throw Exceptions.propagate(e)
        }
        subscriber.onNext(4)
        subscriber.onNext(5)
        subscriber.onComplete()
    }.throttleWithTimeout(800, TimeUnit.MILLISECONDS)
        .subscribe { println(it) }
    println("-------------throttleFirst----------------")
    Observable.create<Int> {
        for (i in 0..9) {
            it.onNext(i)
            //线程休眠100毫秒
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }.throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { println(it) }

    var string = "s1"
    val observable = Observable.defer {
//        Observable.just(string)
        Observable.error<String>(Throwable("你写了个bug"))
    }
    string = "s2"
    observable.subscribe({
        println(it)
    }) {
        println(it.message)
    }

    //没有Schedulers.trampoline()打印不出来
    println("-------------interval----------------")
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline()).take(3)
        .subscribe { println(it) }

    println("-------------repeat----------------")
    Observable.range(1, 5).repeat(4).subscribe {
        print(it)
    }
    println()
    println("-------------timer----------------")
    Observable.timer(2, TimeUnit.SECONDS, Schedulers.trampoline())
        .subscribe {
            println(it)
        }
    println("-------------buffer----------------")
    Observable.just(1, 2, 3, 4, 5).buffer(3).subscribe {
        println("size:${it.size}")
        it.forEach { i ->
            print(i)
        }
    }
    println()
    println("-------------scan----------------")
    Observable.range(1, 5).scan { x, y -> x * y }.subscribe { println(it) }
    println("-------------window----------------")
    Observable.range(1, 5).window(3, 2).subscribe {
        it.subscribe { v ->
            println("window:value:$v")
        }
    }
    println("-------------distinct----------------")
    Observable.just(1, 2, 3, 2, 3).distinct().subscribe { println(it) }
    println("-------------elementAt----------------")
    Observable.just(1, 2, 3, 2, 3).elementAt(2).subscribe { println(it) }
    println("-------------skip----------------")
    Observable.just(1, 2, 3, 2, 3).skip(2).subscribe { println(it) }
    println("-------------skipLast----------------")
    Observable.just(1, 2, 3, 2, 3).skipLast(2).subscribe { println(it) }
    println("-------------take----------------")
    Observable.just(1, 2, 3, 2, 3).take(2).subscribe { println(it) }
    println("-------------takeLast----------------")
    Observable.just(1, 2, 3, 2, 3).takeLast(2).subscribe { println(it) }
    println("-------------first----------------")
    Observable.empty<Int>().first(10).subscribe { t1, _ -> println(t1) }
    println("-------------last----------------")
    Observable.just(1, 2, 3, 2, 6).last(2).subscribe { t1, _ -> println(t1) }
    println("-------------ignoreElements----------------")
    Observable.just(1, 3, 4, 6, 8).ignoreElements().subscribe { println("onComplete") }
    println("-------------groupBy----------------")
    val maps = mutableMapOf<String, MutableList<Int>>()
    Observable.range(1, 5).groupBy {
        println("v:$it")
        if (it % 2 == 0) "偶数" else "奇数"
    }.subscribe {
        println("$it")
        maps[it.key!!] = mutableListOf()
        it.subscribe { v ->
            maps[it.key!!]?.add(v)
        }
    }
    maps.forEach { (s, mutableList) ->
        println("key:$s")
        mutableList.forEach {
            println(it)
        }
    }
    println("-------------zip----------------")
    val observable1 = Observable.just(10, 20, 30)
    val observable2 = Observable.just(2, 4, 5, 8)
    Observable.zip(observable1, observable2) { v1, v2 ->
        v1 + v2
    }.subscribe {
        println("value:$it,thread:${Thread.currentThread().name}")
    }
    println("-------------merge----------------")
    Observable.merge(observable1, observable2).subscribe {
        println(it)
    }
    println("-------------startWith----------------")
    observable2.startWith(observable1).subscribe {
        println(it)
    }
    println("-------------combineLatest----------------")
    Observable.combineLatest(observable1, observable2) { v1, v2 ->
        println("v1:$v1,v2:${v2}")
        v1 + v2
    }.subscribe {
        println("value:$it,thread:${Thread.currentThread().name}")
    }
    println("-------------join----------------")
    observable2.join(observable1,
        { Observable.timer(4, TimeUnit.SECONDS) },
        { Observable.timer(2, TimeUnit.SECONDS) },
        { v1, v2 -> "$v1--&--$v2" }).subscribe {
        println(it)
    }
    println("-------------onErrorReturn----------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onError(java.lang.NullPointerException("NPE"))
        it.onNext(3)
    }.onErrorReturn { 2 }.subscribe {
        println(it)
    }
    println("-------------onErrorReturnItem----------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onError(java.lang.NullPointerException("NPE"))
        it.onNext(3)
    }.onErrorReturnItem(2).subscribe {
        println(it)
    }
    println("-------------retry----------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onError(java.lang.NullPointerException("NPE"))
    }.retry(3).subscribe({
        println(it)
    }) {
        println(it.message)
    }
    println("-------------retryWhen----------------")
    Observable.create<String> {
        println("subscribing")
        it.onError(RuntimeException("always fails"))
    }.retryWhen { attempts: Observable<Throwable?> ->
        attempts.zipWith(
            Observable.range(1, 3)
        ) { _: Throwable?, i: Int -> i }
            .flatMap { i: Int ->
                println("delay retry by $i second(s)")
                Observable.timer(
                    i.toLong(),
                    TimeUnit.SECONDS
                )
            }
    }.blockingForEach {
        println(it)
    }
}