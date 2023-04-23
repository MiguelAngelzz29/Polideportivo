package es.miguel.polideportivo_v2.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.ConfirmarReservaActivity;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;


public class ReservaActividadAdapter extends RecyclerView.Adapter<ReservaActividadAdapter.ViewHolder> {

    private List<ReservaActividad> lista;
    private String email;
    private Context context;

    public ReservaActividadAdapter(List<ReservaActividad> lista, String email, Context context) {
        this.lista = lista;
        this.email = email;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservaActividadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reserva_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaActividadAdapter.ViewHolder holder, int position) {

        String url = lista.get(position).getActividad().getImagen();
        Glide.with(context)
                .load(url)
                .into(holder.imagen);
        holder.descripcion.setText(lista.get(position).getActividad().getDescripcion());
        holder.horario.setText(lista.get(position).getHorario_reservado());
        holder.capacidad.setText(lista.get(position).getActividad().getNumero_reservas()
                + "/" +lista.get(position).getActividad().getCapacidad());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("IMAGEN_RESERVA_ACTIVIDAD", lista.get(position).getActividad().getImagen());
            intent.putExtra("DESCRIPCION_RESERVA_ACTIVIDAD", lista.get(position).getActividad().getDescripcion());
            intent.putExtra("HORARIO_RESERVA_ACTIVIDAD", lista.get(position).getHorario_reservado());
            intent.putExtra("UBICACION_RESERVA_ACTIVIDAD", lista.get(position).getActividad().getUbicacion());
            intent.putExtra("ID_PISTA_RESERVA_ACTIVIDAD", lista.get(position).getActividad().getId_actividad());
            intent.putExtra("CAPACIDAD_RESERVA_ACTIVIDAD",lista.get(position).getActividad().getCapacidad());
            intent.putExtra("NUMERO_RESERVA_ACTIVIDAD",lista.get(position).getActividad().getNumero_reservas());
            intent.putExtra("EMAIL_RESERVA_ACTIVIDAD",this.email);
            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario,descripcion,reservar,capacidad;
        private ImageView imagen;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horario = itemView.findViewById(R.id.tv_horario_actividad);
            descripcion = itemView.findViewById(R.id.tv_tipo_actividad);
            reservar = itemView.findViewById(R.id.tv_reservar_actividad);
            capacidad = itemView.findViewById(R.id.tv_capacidad_reserva_actividad);
            imagen = itemView.findViewById(R.id.iv_foto_actividad);
            layout = itemView.findViewById(R.id.layoutPrincipal);

        }
    }

    public void alternarColorRecyclerView(ReservaActividadAdapter.ViewHolder holder, int position){
        if(position % 2 == 0){
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        }else{
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

}
