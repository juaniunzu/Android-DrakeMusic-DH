package com.example.projectointegrador.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Busqueda;

import org.w3c.dom.Text;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Busqueda> busquedaList;

    public HistorialAdapter(List<Busqueda> busquedaList) {
        this.busquedaList = busquedaList;
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

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvceldahistorial);
        }

        public void onBind(Busqueda busqueda) {
            tv.setText(busqueda.getBusqueda());
        }
    }
}
