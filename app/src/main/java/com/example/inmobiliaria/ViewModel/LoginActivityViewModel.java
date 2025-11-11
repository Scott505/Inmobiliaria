package com.example.inmobiliaria.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.modelo.Propietario;
import com.example.inmobiliaria.request.ApiClient;
import com.example.inmobiliaria.ui.LoginActivity;
import com.example.inmobiliaria.ui.MenuActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mLogin;
    private MutableLiveData<Boolean> mLogout = new MutableLiveData<>();
    public LiveData<Boolean> getLogout() {
        return mLogout;
    }
    public LiveData<Boolean> getmLogin() {
        if (mLogin == null) {
            mLogin = new MutableLiveData<>();
        }
        return mLogin;
    }

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String usuario, String clave) {

        ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
        Call<String> llamada = api.login(usuario, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mLogin.postValue(true);
                    Toast.makeText(getApplication(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    //Cambia la vista
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
        resetLogout();
    }

    public void logout() {
        ApiClient.borrarToken(getApplication());
        mLogout.postValue(true);
    }
    public void resetLogout() {
        mLogout.setValue(false);
    }
}