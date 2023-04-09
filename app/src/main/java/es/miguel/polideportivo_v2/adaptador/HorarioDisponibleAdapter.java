package es.miguel.polideportivo_v2.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.ConfirmarReservaPistaActivity;
import es.miguel.polideportivo_v2.activity.HorarioDisponibleActivity;

public class HorarioDisponibleAdapter extends RecyclerView.Adapter<HorarioDisponibleAdapter.ViewHolder> {

    private List<String> listaHoras;

    public HorarioDisponibleAdapter(List<String> listaDias) {
        this.listaHoras = listaDias;
    }

    @NonNull
    @Override
    public HorarioDisponibleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_horario_disponible,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioDisponibleAdapter.ViewHolder holder, int position) {
        holder.pista.setText(listaHoras.get(position));
        holder.diaLetra.setText(listaHoras.get(position));
        holder.diaNum.setText(listaHoras.get(position));
        holder.horario.setText(listaHoras.get(position));

        //Botón  con Luz
        holder.btn_conLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfirmarReservaPistaActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        //Botón sin Luz
        holder.btn_sinLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfirmarReservaPistaActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        }

    @Override
    public int getItemCount() {
        return listaHoras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pista,diaLetra,diaNum,horario;
        Button btn_conLuz, btn_sinLuz;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pista= itemView.findViewById(R.id.tv_pista);
            diaLetra = itemView.findViewById(R.id.tv_diaLetra_disponible);
            diaNum = itemView.findViewById(R.id.tv_diaNum_disponible);
            horario = itemView.findViewById(R.id.tv_hora_disponible);
            btn_conLuz = itemView.findViewById(R.id.btn_conLuz);
            btn_sinLuz = itemView.findViewById(R.id.btn_sinLuz);
        }
    }
}
