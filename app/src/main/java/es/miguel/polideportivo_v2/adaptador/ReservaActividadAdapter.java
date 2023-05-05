package es.miguel.polideportivo_v2.adaptador;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.ConfirmarReservaActivity;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;


public class ReservaActividadAdapter extends RecyclerView.Adapter<ReservaActividadAdapter.ViewHolder> {

    private List<ReservaActividad> lista;
    private String email;
    private Context context;

    private LocalDateTime fecha_reserva;

    public ReservaActividadAdapter(List<ReservaActividad> lista, String email, Context context, LocalDateTime fecha_reserva) {
        this.lista = lista;
        this.email = email;
        this.context = context;
        this.fecha_reserva = fecha_reserva;
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
        holder.nombre.setText(lista.get(position).getActividad().getNombre());
        holder.horario.setText(lista.get(position).getHorario_reservado());

        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {
            int id_actividad = lista.get(position).getActividad().getId_actividad();
            String imagen = lista.get(position).getActividad().getImagen();
            String nombre = lista.get(position).getActividad().getNombre();
            String horario = lista.get(position).getHorario_reservado();
            String ubicacion = lista.get(position).getActividad().getUbicacion();
            String descripcion = lista.get(position).getActividad().getDescripcion();
            int capacidad = lista.get(position).getActividad().getCapacidad();
            int numero_reservas = lista.get(position).getActividad().getNumero_reservas();


            Intent intent = new Intent(v.getContext(), ConfirmarReservaActivity.class);
            intent.putExtra("ID_RESERVA_ACTIVIDAD", id_actividad);
            intent.putExtra("IMAGEN_RESERVA_ACTIVIDAD", imagen);
            intent.putExtra("NOMBRE_RESERVA_ACTIVIDAD", nombre);
            intent.putExtra("HORARIO_RESERVA_ACTIVIDAD", horario);
            intent.putExtra("UBICACION_RESERVA_ACTIVIDAD", ubicacion);
            intent.putExtra("CAPACIDAD_RESERVA_ACTIVIDAD", capacidad);
            intent.putExtra("NUMERO_RESERVA_ACTIVIDAD", numero_reservas);
            intent.putExtra("EMAIL_RESERVA_ACTIVIDAD", email);
            intent.putExtra("FECHA_RESERVA_ACTIVIDAD", fecha_reserva.toString());
            intent.putExtra("DESCRIPCION_RESERVA_ACTIVIDAD",descripcion);

            v.getContext().startActivity(intent);

        });

        // Iniciar tarea en segundo plano para obtener número de reservas
        GetNumeroReservaTask getNumeroReservaTask = new GetNumeroReservaTask(
                holder,
                position,
                lista.get(position).getActividad().getId_actividad(),
                Timestamp.valueOf(fecha_reserva.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                lista.get(position).getHorario_reservado()
        );
        getNumeroReservaTask.execute();


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario, nombre, reservar, capacidad;
        private ImageView imagen;
        private LinearLayout layout;
        private int id_actividad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            horario = itemView.findViewById(R.id.tv_horario_actividad);
            nombre = itemView.findViewById(R.id.tv_tipo_actividad);
            reservar = itemView.findViewById(R.id.tv_reservar_actividad);
            capacidad = itemView.findViewById(R.id.tv_capacidad_reserva_actividad);
            imagen = itemView.findViewById(R.id.iv_foto_actividad);
            layout = itemView.findViewById(R.id.layoutPrincipal);
        }
    }

    public void alternarColorRecyclerView(ReservaActividadAdapter.ViewHolder holder, int position){
        if(position % 2 == 0) {
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        } else {
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

    private class GetNumeroReservaTask extends AsyncTask<Void, Void, Integer> {

        private ViewHolder holder;
        private int position;
        private int id;
        private Timestamp timestamp;
        private String hora;

        public GetNumeroReservaTask(ViewHolder holder, int position, int id, Timestamp timestamp, String hora) {
            this.holder = holder;
            this.position = position;
            this.id = id;
            this.timestamp = timestamp;
            this.hora = hora;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ConexionDB conexionBD = new ConexionDB();
            return conexionBD.getNumeroReservas(id, timestamp, hora);
        }

        @Override
        protected void onPostExecute(Integer result) {
            lista.get(position).getActividad().setNumero_reservas(result);

            // Actualizar capacidad en el hilo principal
            holder.capacidad.setText(
                    result + "/" +
                            lista.get(position).getActividad().getCapacidad()
            );
        }
    }
}