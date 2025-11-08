package com.example.inmobiliaria.ui.ui.contrato;

import static com.example.inmobiliaria.request.ApiClient.URLBASE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.modelo.Inmueble;

import java.util.List;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ContratosViewHolder>{
    private List<Inmueble> lista;
    private Context context;
    public ContratosAdapter(List<Inmueble> inmuebles, Context context) {
        this.lista = inmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public ContratosAdapter.ContratosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contratos_card, parent, false);
        return new ContratosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratosAdapter.ContratosViewHolder holder, int position) {
        Inmueble inmueble = lista.get(position);
        holder.tvContratoDireccion.setText(inmueble.getDireccion());

        Glide.with(context)
                .load(URLBASE + inmueble.getImagen())
                .error("null")
                .into(holder.ivInmuebleContrato);

        holder.btnVerContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);
                Navigation.findNavController(v).navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class ContratosViewHolder extends RecyclerView.ViewHolder {
        TextView tvContratoDireccion;
        Button btnVerContrato;
        ImageView ivInmuebleContrato;
        public ContratosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContratoDireccion = itemView.findViewById(R.id.tvContratoDireccion);
            btnVerContrato = itemView.findViewById(R.id.btnVerContrato);
            ivInmuebleContrato = itemView.findViewById(R.id.ivInmuebleContrato);
        }
    }
}
