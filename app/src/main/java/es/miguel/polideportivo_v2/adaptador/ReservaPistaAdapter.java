package es.miguel.polideportivo_v2.adaptador;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.ConfirmarReservaActivity;
import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.ReservaPistas;


public class ReservaPistaAdapter extends RecyclerView.Adapter<ReservaPistaAdapter.ViewHolder> {

    private List<ReservaPistas> listaActividades;


    public ReservaPistaAdapter(List<ReservaPistas> listaActividades) {
        this.listaActividades = listaActividades;
    }

    @NonNull
    @Override
    public ReservaPistaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reserva_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaPistaAdapter.ViewHolder holder, int position) {
        int imagenId = listaActividades.get(position).getPista().getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(listaActividades.get(position).getPista().getTipo_deporte());
        holder.horario.setText(listaActividades.get(position).getHorario_reservado());
        holder.ubicacion.setText(listaActividades.get(position).getPista().getUbicacion());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("IMAGEN_RESERVA",listaActividades.get(position).getPista().getImagen());
            intent.putExtra("DESCRIPCION_RESERVA",listaActividades.get(position).getPista().getTipo_deporte());
            intent.putExtra("HORARIO_RESERVA", listaActividades.get(position).getHorario_reservado());
            intent.putExtra("UBICACION_RESERVA",listaActividades.get(position).getPista().getUbicacion());
            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario,descripcion,reservar,ubicacion;
        private ImageView imagen;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horario = itemView.findViewById(R.id.tv_horario_actividad);
            descripcion = itemView.findViewById(R.id.tv_tipo_actividad);
            reservar = itemView.findViewById(R.id.tv_reservar_actividad);
            ubicacion = itemView.findViewById(R.id.tv_capacidad_reserva_actividad);
            imagen = itemView.findViewById(R.id.iv_foto_actividad);
            layout = itemView.findViewById(R.id.layoutPrincipal);

        }
    }

    public void alternarColorRecyclerView(ReservaPistaAdapter.ViewHolder holder, int position){
        if(position % 2 == 0){
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        }else{
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

}








/*
public class ReservaPistaAdapter extends FirestoreRecyclerAdapter<Pista, ReservaPistaAdapter.ViewHolder> {




    public ReservaPistaAdapter(@NonNull FirestoreRecyclerOptions<Pista> options) {
        super(options);

    }

    @NonNull
    @Override
    public ReservaPistaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reserva_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaPistaAdapter.ViewHolder holder, int position, Pista pista) {
        int imagenId = pista.getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(pista.getTipo_deporte());
      //  holder.horario.setText(pista.getHorario().toString());
        holder.ubicacion.setText(pista.getUbicacion());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("IMAGEN_PISTA", pista.getImagen());
            intent.putExtra("DESCRIPCION_PISTA", pista.getTipo_deporte());
          //  intent.putExtra("HORARIO_PISTA", pista.getHorario());
            intent.putExtra("UBICACION_PISTA", pista.getUbicacion());
            v.getContext().startActivity(intent);

        });

        }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario,descripcion,reservar,ubicacion;
        private ImageView imagen;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horario = itemView.findViewById(R.id.tv_horario_actividad);
            descripcion = itemView.findViewById(R.id.tv_tipo_actividad);
            reservar = itemView.findViewById(R.id.tv_reservar_actividad);
            ubicacion = itemView.findViewById(R.id.tv_capacidad_actividad);
            imagen = itemView.findViewById(R.id.iv_foto_actividad);
            layout = itemView.findViewById(R.id.layoutPrincipal);

        }
    }

    public void alternarColorRecyclerView(ReservaPistaAdapter.ViewHolder holder, int position){
        if(position % 2 == 0){
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        }else{
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

    }
*/
