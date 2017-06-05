package com.closedevice.fastapp.api.convert.bk;

import com.closedevice.fastapp.api.exception.ApiException;
import com.closedevice.fastapp.model.response.bk.BKResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by LSD on 2017/4/22.
 */

public class BKGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public BKGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BKResult re = mGson.fromJson(response, BKResult.class);
        if (!re.isOk()) {
            value.close();
            if (re.getResults() != null) {
                String errorExtendMsg = mGson.toJson(re.getResults());
                throw new ApiException(re.getCode(), re.getMsg(), errorExtendMsg);
            } else {
                throw new ApiException(re.getCode(), re.getMsg());
            }
        }

        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(bis, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
