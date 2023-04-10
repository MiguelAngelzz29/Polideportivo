package es.miguel.polideportivo_v2.adaptador;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.JsonUtils;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.ConfirmarReservaActivity;

public class HorarioDisponibleAdapter extends RecyclerView.Adapter<HorarioDisponibleAdapter.ViewHolder> {

    private List<String> listaHoras;
    private boolean seleccionaPista;


    public HorarioDisponibleAdapter(List<String> listaHoras, boolean seleccionaPista) {
        this.listaHoras = listaHoras;
        this.seleccionaPista = seleccionaPista;
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

        if(!seleccionaPista){
            holder.btn_conLuz.setVisibility(View.GONE);
            holder.layout.setPadding(20,80,20,0);

        }
        holder.btn_conLuz.setOnClickListener( v ->{
                Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
                v.getContext().startActivity(intent);

        });

        //Botón sin Luz
        holder.btn_sinLuz.setOnClickListener( v -> {
                Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
                v.getContext().startActivity(intent);

        });
        }

    @Override
    public int getItemCount() {
        return listaHoras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pista,diaLetra,diaNum,horario,btn_conLuz, btn_sinLuz;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pista= itemView.findViewById(R.id.tv_pista);
            diaLetra = itemView.findViewById(R.id.tv_diaLetra_disponible);
            diaNum = itemView.findViewById(R.id.tv_diaNum_disponible);
            horario = itemView.findViewById(R.id.tv_hora_disponible);
            btn_conLuz = itemView.findViewById(R.id.btn_conLuz);
            btn_sinLuz = itemView.findViewById(R.id.btn_sinLuz);
            layout = itemView.findViewById(R.id.layoutLuz);
        }
    }
}
