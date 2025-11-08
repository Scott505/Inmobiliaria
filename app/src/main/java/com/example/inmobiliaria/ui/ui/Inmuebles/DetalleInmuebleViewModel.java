package com.example.inmobiliaria.ui.ui.Inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }


    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble = (Inmueble) inmuebleBundle.getSerializable("inmueble");

        if (inmueble != null){
            mInmueble.setValue(inmueble);
        }
    }

    public void actualizarInmueble(Boolean estado){
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(estado);
        inmueble.setIdInmueble(this.mInmueble.getValue().getIdInmueble());
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<Inmueble> llamada = api.actualizarInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response){
                if (response.isSuccessful()){
                    if(estado){
                        Toast.makeText(getApplication(), "Inmueble ahora disponible", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "Inmueble ahora NO disponible", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(), "Error al actualizar el inmueble", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de servidor VMInmuebles", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
