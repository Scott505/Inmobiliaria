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
import com.example.inmobiliaria.modelo.Pagos;
import com.example.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {
    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
    }
    private final MutableLiveData<List<Pagos>> listaPagos = new MutableLiveData<>();

    public LiveData<List<Pagos>> getListaPagos() {
        return listaPagos;
    }

    public void cargarPagosPorContrato(Bundle contratoBundle){

        Contrato contrato = (Contrato) contratoBundle.getSerializable("contrato");

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<List<Pagos>> llamada = api.obtenerPagosPorContrato("Bearer " + token, contrato.getIdContrato());
        llamada.enqueue(new Callback<List<Pagos>>(){
          @Override
          public void onResponse(Call<List<Pagos>> call, Response<List<Pagos>> response){
              if (response.isSuccessful() && response.body() != null) {
                  listaPagos.postValue(response.body());
              } else {
                  Toast.makeText(getApplication(), "No se encontraron pagos", Toast.LENGTH_SHORT).show();
              }
          }
          @Override
          public void onFailure(Call<List<Pagos>> call, Throwable t) {
            Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
          }
        });
    }
}
