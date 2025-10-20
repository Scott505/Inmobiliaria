package com.example.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    private static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmobiliariaService getInmobiliariaService(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);

    }

    public interface InmobiliariaService{
        @FormUrlEncoded
        @POST("api/propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/propietarios")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

        @PUT("api/propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarClave(@Header("Authorization") String token, @Field("currentPassword") String claveActual, @Field("newPassword") String claveNueva);

    }

    public static void guardarToken(Context context, String token){
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
    public static String obtenerToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

}
