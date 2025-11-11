package com.example.inmobiliaria.ui.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetallePagosBinding;

public class DetallePagosFragment extends Fragment {

    private DetallePagosViewModel mViewModel;
    private FragmentDetallePagosBinding binding;

    public static DetallePagosFragment newInstance() {
        return new DetallePagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetallePagosViewModel.class);
        binding = FragmentDetallePagosBinding.inflate(getLayoutInflater());

        mViewModel.cargarPagosPorContrato(getArguments());

        mViewModel.getListaPagos().observe(getViewLifecycleOwner(), pagos -> {
            PagosAdapter adapter = new PagosAdapter(pagos, getContext());
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            RecyclerView rv = binding.rvPagos;
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}