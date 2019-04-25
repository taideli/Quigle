package site.taideli.pump;

import org.slf4j.Logger;
import site.taideli.pump.base.Runnable;
import site.taideli.pump.sink.Enqueue;
import site.taideli.pump.sink.Sink;
import site.taideli.pump.source.Dequeue;
import site.taideli.pump.source.Source;

import java.util.function.Function;
import java.util.stream.Stream;

public interface Wrapper {

    static <T> WrapSource<T> wrap(Source<?> source, Dequeue<T> dequeue) {
        return new WrapSource<T>(source) {

            @Override
            public long dequeue(Function<Stream<T>, Long> using, long batch) {
                return dequeue.dequeue(using, batch);
            }
        };
    }

    static <T> WrapSink<T> wrap(Sink<?> sink, Enqueue<T> enqueue) {
        return new WrapSink<T>(sink) {

            @Override
            public long enqueue(Stream<T> items) {
                return enqueue.enqueue(items);
            }
        };
    }

    abstract class WrapSource<V> implements Source<V> {

        protected final Source<?> source;

        protected WrapSource(Source<?> source) {
            this.source = source;
        }

        @Override
        public long size() {
            return source.size();
        }

        @Override
        public long capacity() {
            return source.capacity();
        }

        @Override
        public boolean empty() {
            return source.empty();
        }

        @Override
        public boolean full() {
            return source.full();
        }

        @Override
        public boolean opened() {
            return source.opened();
        }

        @Override
        public boolean closed() {
            return source.closed();
        }

        @Override
        public void opening(Runnable handler) {
            source.opening(handler);
        }

        @Override
        public void closing(Runnable handler) {
            source.closing(handler);
        }

        @Override
        public void open() {
            source.open();
        }

        @Override
        public void close() {
            source.close();
        }

        @Override
        public Logger logger() {
            return source.logger();
        }

        @Override
        public String name() {
            return source.name();
        }

        @Override
        public String toString() {
            return source.toString() + "WrapSource";
        }
    }


    abstract class WrapSink<V> implements Sink<V> {

        protected final Sink<?> sink;

        protected WrapSink(Sink<?> sink) {
            this.sink = sink;
        }

        @Override
        public long size() {
            return sink.size();
        }

        @Override
        public long capacity() {
            return sink.capacity();
        }

        @Override
        public boolean empty() {
            return sink.empty();
        }

        @Override
        public boolean full() {
            return sink.full();
        }

        @Override
        public boolean opened() {
            return sink.opened();
        }

        @Override
        public boolean closed() {
            return sink.closed();
        }

        @Override
        public void opening(Runnable handler) {
            sink.opening(handler);
        }

        @Override
        public void closing(Runnable handler) {
            sink.closing(handler);
        }

        @Override
        public void open() {
            sink.open();
        }

        @Override
        public void close() {
            sink.close();
        }

        @Override
        public Logger logger() {
            return sink.logger();
        }

        @Override
        public String name() {
            return sink.name();
        }

        @Override
        public String toString() {
            return sink.toString() + "WrapSink";
        }
    }
}
