package com.example.inmobiliaria.ui.ui.Inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Inmueble>> getmInmueble() {
        return mInmueble;
    }


    public void cargarInmuebles(){
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<Inmueble>> llamada = api.obtenerInmuebles("Bearer " + token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response){
                if (response.isSuccessful()){
                    mInmueble.postValue(response.body());
                }else {
                    Toast.makeText(getApplication(), "No hay inmuebles disponibles", Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.e("API_ERROR", "Falla al obtener inmuebles", t);
                Toast.makeText(getApplication(), "Error de servidor"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }





}
