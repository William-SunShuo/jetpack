package com.example.rx
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val tag = "TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Observable.timer(5, TimeUnit.SECONDS,AndroidSchedulers.mainThread())
            .subscribe {
                Log.i(tag, "timer,thread:${Thread.currentThread().name}")
            }
        Observable
            .just(1, 2, 3, 4, 5)
            .filter {
                Log.i(tag, "filter,thread:${Thread.currentThread().name}")
                it > 3
            }
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { i: Int ->
                Log.i(tag, "map,thread:${Thread.currentThread().name}")
                i + 1
            }.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(tag, "onSubscribe,thread:${Thread.currentThread().name}")
                }

                override fun onNext(t: Int) {
                    Log.i(tag, "onNext:$t,thread:${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable) {
                    Log.i(tag, "onError,thread:${Thread.currentThread().name}")
                }

                override fun onComplete() {
                    Log.i(tag, "onComplete,thread:${Thread.currentThread().name}")
                }
            })
    }
}
