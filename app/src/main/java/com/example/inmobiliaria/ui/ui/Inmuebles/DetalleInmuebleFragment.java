package com.example.inmobiliaria.ui.ui.Inmuebles;

import static com.example.inmobiliaria.request.ApiClient.URLBASE;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;
    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(getLayoutInflater());

        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvId.setText(inmueble.getIdInmueble()+"");
            binding.tvDireccioni.setText(inmueble.getDireccion());
            binding.tvUso.setText(inmueble.getUso());
            binding.tvValor.setText(String.valueOf(inmueble.getValor()));
            binding.tvAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
            binding.tvLongitud.setText(String.valueOf(inmueble.getLongitud()));
            binding.tvLatitud.setText(String.valueOf(inmueble.getLatitud()));
            binding.cbDisponibleInmuebles.setChecked(inmueble.isDisponible());
            Glide.with(getContext())
                    .load(URLBASE+inmueble.getImagen())
                    .error("null")
                    .into(binding.ivDetInmuebles);
        });

        mViewModel.obtenerInmueble(getArguments());
        binding.cbDisponibleInmuebles.setOnClickListener(v -> {
            mViewModel.actualizarInmueble(binding.cbDisponibleInmuebles.isChecked());
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}