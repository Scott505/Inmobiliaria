package com.example.inmobiliaria.ui.ui.contrato;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        cargarInmueblesConContrato();
    }
    public LiveData<List<Inmueble>> getmInmueble() {
        return mInmueble;
    }

    public void cargarInmueblesConContrato(){
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<Inmueble>> llamada = api.obtenerInmueblesConContrato("Bearer " + token);

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
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}