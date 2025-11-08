package com.example.inmobiliaria.ui.ui.Inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    public LiveData<Uri> getmUri() {
        return mUri;
    }

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();
            mUri.setValue(uri);
        }
    }

    public void agregarInmueble(String direccion, String tipo, String superficie, String uso, String ambientes, String precio){
        int superficieInt, ambientesInt;
        double precioDouble;

        try {
            superficieInt = Integer.parseInt(superficie);
            ambientesInt = Integer.parseInt(ambientes);
            precioDouble = Double.parseDouble(precio);

            if(direccion.isEmpty() || tipo.isEmpty() || superficie.isEmpty() || uso.isEmpty()){
                Toast.makeText(getApplication(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mUri.getValue() == null){
                Toast.makeText(getApplication(), "Seleccione una foto", Toast.LENGTH_SHORT).show();
                return;
            }

            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setTipo(tipo);
            inmueble.setSuperficie(superficieInt);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientesInt);
            inmueble.setValor(precioDouble);
            inmueble.setDisponible(false);

            byte[] imagen = convertirImagen();
            String inmuebleJson = new Gson().toJson(inmueble); //Convierte inmueble en JSON

            // JSON e imagen como RequestBody
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            //Imagen a Multipart
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            ApiClient.InmobiliariaService api = ApiClient.getInmobiliariaService();
            String token = ApiClient.obtenerToken(getApplication());
            Call<Inmueble> llamada = api.agregarInmueble("Bearer " + token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble agregado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "Error al agregar inmueble", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (NumberFormatException e){
            Toast.makeText(getApplication(), "Error en los valores ingresados (Se espera un número)", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private byte[] convertirImagen(){
        try {
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (FileNotFoundException e){
            Toast.makeText(getApplication(), "No selecciono una imagen", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }


    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
}