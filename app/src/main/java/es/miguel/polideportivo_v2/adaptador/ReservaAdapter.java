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
import es.miguel.polideportivo_v2.dominio.ReservaPista;


public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolder> {

    private List<ReservaPista> lista;
    private String email;
    private Context context;

    public ReservaAdapter(List<ReservaPista> lista, String email, Context context) {
        this.lista = lista;
        this.email = email;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reserva_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaAdapter.ViewHolder holder, int position) {

        String url = lista.get(position).getPista().getImagen();
        Glide.with(context)
                .load(url)
                .into(holder.imagen);
        holder.descripcion.setText(lista.get(position).getPista().getTipo_deporte());
        holder.horario.setText(lista.get(position).getHorario_reservado());
        holder.ubicacion.setText(lista.get(position).getPista().getUbicacion());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("IMAGEN_RESERVA", lista.get(position).getPista().getImagen());
            intent.putExtra("DESCRIPCION_RESERVA", lista.get(position).getPista().getTipo_deporte());
            intent.putExtra("HORARIO_RESERVA", lista.get(position).getHorario_reservado());
            intent.putExtra("UBICACION_RESERVA", lista.get(position).getPista().getUbicacion());
            intent.putExtra("ID_PISTA_RESERVA", lista.get(position).getPista().getId_pista());
            intent.putExtra("EMAIL_RESERVA",this.email);
            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
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

    public void alternarColorRecyclerView(ReservaAdapter.ViewHolder holder, int position){
        if(position % 2 == 0){
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        }else{
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

}
