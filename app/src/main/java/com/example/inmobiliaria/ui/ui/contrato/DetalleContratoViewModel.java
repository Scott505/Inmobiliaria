package com.example.inmobiliaria.ui.ui.contrato;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Contrato;
import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    public LiveData<Contrato> getmContrato() {
        return mContrato;
    }

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtenerContrato(Bundle contratoBundle){
        Inmueble inmueble = (Inmueble) contratoBundle.getSerializable("inmueble");

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<Contrato> llamada = api.obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble());

        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mContrato.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se encontró contrato", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }


}