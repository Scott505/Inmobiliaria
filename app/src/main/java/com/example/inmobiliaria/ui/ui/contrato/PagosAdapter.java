package com.example.inmobiliaria.ui.ui.contrato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.modelo.Pagos;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.PagosViewHolder> {

    private List<Pagos> listaPagos;
    private Context context;

    public PagosAdapter(List<Pagos> listaPagos, Context context) {
        this.listaPagos = listaPagos;
    }

    @NonNull
    @Override
    public PagosAdapter.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pagos_card, parent, false);
        return new PagosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.PagosViewHolder holder, int position) {
        Pagos pago = listaPagos.get(position);

        holder.tvPagoNumero.setText("Pago N°: " + pago.getIdPago());
        holder.tvPagoMonto.setText("Monto: $" + pago.getMonto());
        holder.tvPagoFecha.setText("Fecha: " + pago.getFechaPago());
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class PagosViewHolder extends RecyclerView.ViewHolder {
        TextView tvPagoNumero, tvPagoMonto, tvPagoFecha;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPagoNumero = itemView.findViewById(R.id.tvPagoNumero);
            tvPagoMonto = itemView.findViewById(R.id.tvPagoMonto);
            tvPagoFecha = itemView.findViewById(R.id.tvPagoFecha);
        }
    }
}