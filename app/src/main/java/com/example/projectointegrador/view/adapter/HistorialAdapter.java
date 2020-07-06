package com.example.projectointegrador.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Busqueda;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Busqueda> busquedaList;
    private HistorialAdapterListener listener;

    public HistorialAdapter(List<Busqueda> busquedaList, HistorialAdapterListener listener) {
        this.busquedaList = busquedaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_historial,parent,false);
        HistorialViewHolder historialViewHolder = new HistorialAdapter.HistorialViewHolder(view);
        return historialViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Busqueda busqueda = busquedaList.get(position);
        holder.onBind(busqueda);
    }

    @Override
    public int getItemCount() {
        return busquedaList.size();
    }

    protected class HistorialViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private ImageView iv;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvceldahistorial);
            iv = itemView.findViewById(R.id.celdaHistorialBorrarBusquedaIndividual);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Busqueda busqueda = busquedaList.get(getAdapterPosition());
                    listener.onClickBusqueda(busqueda);
                }
            });

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Busqueda busqueda = busquedaList.get(getAdapterPosition());
                    listener.onClickBorrarBusqueda(busqueda);
                }
            });
        }

        public void onBind(Busqueda busqueda) {
            tv.setText(busqueda.getBusqueda());
        }
    }

    public interface HistorialAdapterListener {
        void onClickBusqueda (Busqueda busqueda);
        void onClickBorrarBusqueda(Busqueda busqueda);
    }
}
