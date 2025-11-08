package com.example.inmobiliaria.ui.ui.Inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.example.inmobiliaria.databinding.FragmentInmueblesBinding;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel mViewModel;
    private FragmentAgregarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static AgregarInmuebleFragment newInstance() {
        return new AgregarInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        binding = FragmentAgregarInmuebleBinding.inflate(getLayoutInflater());
        abrirGaleria();
        binding.btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });


        mViewModel.getmUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivCInmuebleFoto.setImageURI(uri);
            }
        });

        binding.btnAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.agregarInmueble(binding.etCDireccion.getText().toString(),
                        binding.etCTipo.getText().toString(),
                        binding.etCSuperficie.getText().toString(),
                        binding.etCUso.getText().toString(),
                        binding.etCAmbientes.getText().toString(),
                        binding.etCPrecio.getText().toString());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

    private void abrirGaleria(){
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
               mViewModel.recibirFoto(result);
            }
        });
    }


}