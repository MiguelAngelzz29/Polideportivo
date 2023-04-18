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

/*public class ReservaActividadesAdapter extends RecyclerView.Adapter<ReservaPistaAdapter.ViewHolder> {

    private List<Actividad> listaActividades;


    public ReservaActividadesAdapter(List<Actividad> listaActividades) {
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
        int imagenId = listaActividades.get(position).getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(listaActividades.get(position).getDescripcion());
        holder.horario.setText(listaActividades.get(position).getHorario());
        holder.capacidad.setText(""+listaActividades.get(position).getCapacidad());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("IMAGEN",listaActividades.get(position).getImagen());
            intent.putExtra("DESCRIPCION",listaActividades.get(position).getDescripcion());
            intent.putExtra("HORARIO", listaActividades.get(position).getHorario());
            intent.putExtra("CAPACIDAD",listaActividades.get(position).getCapacidad());
            v.getContext().startActivity(intent);

        });

        }

    @Override
    public int getItemCount() {
        return listaActividades.size();
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
            capacidad = itemView.findViewById(R.id.tv_capacidad_actividad);
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

    }*/
