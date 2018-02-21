package a_intro;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RxJavaCodeExamples {

    public static void main(String[] args) throws Exception {
        //1. doing things in parallel, using multiple threads for IO
        Flowable.just("IBM", "Intel", "Apple")
                .flatMapSingle(RxJavaCodeExamples::getPrice)
                .doOnNext(price -> System.out.println(Thread.currentThread() + ": got price " + price))
                .toList()
                .map(prices -> prices.stream().mapToDouble(value -> value).average().getAsDouble())
                .subscribe(averagePrice -> System.out.println(Thread.currentThread() + ": Average price is " + averagePrice));

        TimeUnit.SECONDS.sleep(5);

        //2. backpressure handling
        Observable<Integer> fastProducer = Observable.range(1, 100)
                .doOnNext(integer -> System.out.println(Thread.currentThread() + ": Produced " + integer))
                .subscribeOn(Schedulers.io());
        Flowable<Integer> droppingProducer = fastProducer.toFlowable(BackpressureStrategy.DROP);
        droppingProducer
                .observeOn(Schedulers.io(), false, 10)
                .subscribe(
                        integer -> {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(Thread.currentThread() + ": Consumed " + integer);
                        },
                        Throwable::printStackTrace,
                        () -> System.out.println("Done")
                );

        TimeUnit.SECONDS.sleep(5);

        //3. zipping
        Single.zip(
                getPrice("flight"),
                getPrice("hotel"),
                getPrice("car"),
                (price1, price2, price3) -> price1 + price2 + price3
        ).subscribe(totalPrice -> System.out.println("Total price for flight, hotal and car is: " + totalPrice));

        TimeUnit.MINUTES.sleep(1);
    }

    private static Single<Double> getPrice(String symbol) {
        return Single.timer(ThreadLocalRandom.current().nextLong(1000), TimeUnit.MILLISECONDS, Schedulers.io())
                .map(ticker -> ThreadLocalRandom.current().nextDouble(200, 800));
    }
}
