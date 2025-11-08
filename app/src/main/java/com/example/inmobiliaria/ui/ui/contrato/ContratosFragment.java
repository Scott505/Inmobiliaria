package com.example.inmobiliaria.ui.ui.contrato;

import androidx.lifecycle.Observer;
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

import com.example.inmobiliaria.databinding.FragmentContratosBinding;
import com.example.inmobiliaria.modelo.Inmueble;
import com.example.inmobiliaria.ui.ui.Inmuebles.InmuebleAdapter;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel viewModel;
    private FragmentContratosBinding binding;


    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        viewModel.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                ContratosAdapter adapter = new ContratosAdapter(inmuebles, getContext());
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvContratos;
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}