package com.jijijija.appproforma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProformaAdapter extends RecyclerView.Adapter<ProformaAdapter.ProformaViewHolder> {
public class ProformaAdapter extends RecyclerView.Adapter<ProformaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final List<ProformaItem> lista;
    private final OnItemClickListener listener;

    public ProformaAdapter(List<ProformaItem> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProformaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_proforma_registro, parent, false);
        return new ProformaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProformaViewHolder holder, int position) {
        ProformaItem item = lista.get(position);

        holder.tvProductoNombre.setText(item.getProducto());
        holder.tvDetalleSecundario.setText(String.format(
                Locale.US,
                "Cód: %s  ·  S/. %.2f  x  %d",
                item.getCodigo(),
                item.getPrecio(),
                item.getCantidad()
        ));
        holder.tvTotalItem.setText(String.format(Locale.US, "S/. %.2f", item.getTotal()));

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(adapterPosition);
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_proforma_registro, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProformaItem item = lista.get(position);

        holder.tvProductoNombre.setText(item.getProducto());
        holder.tvDetalleSecundario.setText(String.format(Locale.getDefault(),
                "Cód: %s  ·  S/. %.2f  x  %d",
                item.getCodigo(), item.getPrecio(), item.getCantidad()));
        holder.tvTotalItem.setText(String.format(Locale.getDefault(),
                "S/. %.2f", item.getTotal()));

        holder.itemView.setOnClickListener(v -> {
            int posicionActual = holder.getBindingAdapterPosition();
            if (posicionActual != RecyclerView.NO_POSITION) {
                listener.onItemClick(posicionActual);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ProformaViewHolder extends RecyclerView.ViewHolder {
        final TextView tvProductoNombre;
        final TextView tvDetalleSecundario;
        final TextView tvTotalItem;

        ProformaViewHolder(@NonNull View itemView) {
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductoNombre, tvDetalleSecundario, tvTotalItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductoNombre = itemView.findViewById(R.id.tvProductoNombre);
            tvDetalleSecundario = itemView.findViewById(R.id.tvDetalleSecundario);
            tvTotalItem = itemView.findViewById(R.id.tvTotalItem);
        }
    }
}
