package org.qsheker.weathersdk.web.mappers;

public interface Mapper<F,T> {
    T toDto(F from);
    F toObject(T to);
}
