package es.miguel.polideportivo_v2.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miguel.polideportivo_v2.R;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder> {

    private List<String> listaHoras;

    public HorarioAdapter(List<String> listaDias) {
        this.listaHoras = listaDias;
    }

    @NonNull
    @Override
    public HorarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_horario,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioAdapter.ViewHolder holder, int position) {
        holder.horario1.setText(listaHoras.get(position));


        }

    @Override
    public int getItemCount() {
        return listaHoras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario1,horario2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horario1= itemView.findViewById(R.id.btn_horario);

        }
    }
}
