package com.example.inmobiliaria.ui.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mViewModel;
    private FragmentDetalleContratoBinding binding;
    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(getLayoutInflater());

        mViewModel.obtenerContrato(getArguments());

        mViewModel.getmContrato().observe(getViewLifecycleOwner(), contrato -> {
            binding.tvCodigo.setText(contrato.getIdContrato()+"");
            binding.tvFechaInicio.setText(contrato.getFechaInicio().toString());
            binding.tvFechaFinal.setText(contrato.getFechaFinalizacion().toString());
            binding.tvMonto.setText(contrato.getMontoAlquiler()+"");
            binding.tvInquilino.setText(contrato.getInquilino().getNombre());
            binding.tvDetalleContratoInmueble.setText(contrato.getInmueble().getDireccion());
        });


        return binding.getRoot();
    }

}