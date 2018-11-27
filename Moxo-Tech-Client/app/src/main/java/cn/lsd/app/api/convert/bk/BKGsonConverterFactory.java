package cn.lsd.app.api.convert.bk;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by LSD on 2017/4/22.
 */

public class BKGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private BKGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        }

        public static BKGsonConverterFactory create() {
            return create(new Gson());
        }

        public static BKGsonConverterFactory create(Gson gson) {
            return new BKGsonConverterFactory(gson);
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new BKGsonResponseBodyConverter<>(gson, adapter);
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new BKGsonRequestBodyConverter<>(gson, adapter);
        }
        }

