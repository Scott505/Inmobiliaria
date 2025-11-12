package com.example.inmobiliaria.ui.ui.salir;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentSalirBinding;
import com.example.inmobiliaria.ui.LoginActivity;

public class SalirFragment extends Fragment {

    private SalirViewModel mViewModel;
    private FragmentSalirBinding binding;

    public static SalirFragment newInstance() {
        return new SalirFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSalirBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SalirViewModel.class);

        mViewModel.getSalir().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean salir) {
                if (salir) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }
        });

        mostrarDialogo();

        return binding.getRoot();
    }

    private void mostrarDialogo() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Salir")
                .setMessage("¿Seguro que quieres cerrar la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> mViewModel.confirmarSalir())
                .setNegativeButton("Cancelar", (dialog, which) ->{
                    mViewModel.cancelarSalir();
                    Navigation.findNavController(requireView()).popBackStack();
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}