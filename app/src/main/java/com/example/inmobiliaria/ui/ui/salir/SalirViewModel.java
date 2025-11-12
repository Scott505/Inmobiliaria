package com.example.inmobiliaria.ui.ui.salir;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.request.ApiClient;

public class SalirViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> salir = new MutableLiveData<>();

    public LiveData<Boolean> getSalir() {
        return salir;
    }


    public SalirViewModel(@NonNull Application application) {
        super(application);
    }

    public void confirmarSalir() {
        ApiClient.borrarToken(getApplication());
        salir.setValue(true);
    }

    public void cancelarSalir() {
        salir.setValue(false);
    }

}