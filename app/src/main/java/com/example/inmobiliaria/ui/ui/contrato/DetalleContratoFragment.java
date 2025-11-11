package com.example.inmobiliaria.ui.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliaria.modelo.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mViewModel;
    private FragmentDetalleContratoBinding binding;
    private Contrato contrato;
    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(getLayoutInflater());

        mViewModel.obtenerContrato(getArguments());

        mViewModel.getmContrato().observe(getViewLifecycleOwner(), c -> {
            contrato = c;
            binding.tvCodigo.setText(c.getIdContrato()+"");
            binding.tvFechaInicio.setText(c.getFechaInicio().toString());
            binding.tvFechaFinal.setText(c.getFechaFinalizacion().toString());
            binding.tvMonto.setText(c.getMontoAlquiler()+"");
            binding.tvInquilino.setText(c.getInquilino().getNombre());
            binding.tvDetalleContratoInmueble.setText(c.getInmueble().getDireccion());

            binding.btnPagos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contrato", contrato);
                    Navigation.findNavController(v).navigate(R.id.detallePagosFragment, bundle);
                }
            });
        });


        return binding.getRoot();
    }

}