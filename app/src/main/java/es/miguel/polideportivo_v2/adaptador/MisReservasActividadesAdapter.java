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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.EliminarReservaActivity;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class MisReservasActividadesAdapter extends RecyclerView.Adapter<MisReservasActividadesAdapter.ViewHolder> {

 private List<ReservaActividad> lista;
 private String email;
 private Context context;

 public MisReservasActividadesAdapter(List<ReservaActividad> lista, String email, Context context) {
  this.lista = lista;
  this.email = email;
  this.context = context;
 }

 @NonNull
 @Override
 public MisReservasActividadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mis_reservas,parent,false);
  return new MisReservasActividadesAdapter.ViewHolder(view);
 }

 @Override
 public void onBindViewHolder(@NonNull MisReservasActividadesAdapter.ViewHolder holder, int position) {

  String url = lista.get(position).getActividad().getImagen();
  Glide.with(context)
          .load(url)
          .into(holder.imagen);
  holder.descripcion.setText(lista.get(position).getActividad().getNombre());
  holder.horario.setText(lista.get(position).getHorario_reservado());
  holder.ubicacion.setText(lista.get(position).getActividad().getUbicacion());
  LocalDateTime fecha = lista.get(position).getFecha_reserva();
  holder.fecha.setText(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

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
   String id_reserva = lista.get(position).getId_reserva_actividad();

   Intent intent = new Intent (v.getContext(), EliminarReservaActivity.class);
   intent.putExtra("ID_RESERVA_ACT",id_reserva);
   intent.putExtra("ID_RESERVA_ACTIVIDAD", id_actividad);
   intent.putExtra("IMAGEN_RESERVA_ACTIVIDAD", imagen);
   intent.putExtra("NOMBRE_RESERVA_ACTIVIDAD", nombre);
   intent.putExtra("HORARIO_RESERVA_ACTIVIDAD", horario);
   intent.putExtra("UBICACION_RESERVA_ACTIVIDAD", ubicacion);
   intent.putExtra("CAPACIDAD_RESERVA_ACTIVIDAD", capacidad);
   intent.putExtra("NUMERO_RESERVA_ACTIVIDAD", numero_reservas);
   intent.putExtra("EMAIL_RESERVA_ACTIVIDAD", email);
   intent.putExtra("FECHA_RESERVA_ACTIVIDAD", fecha.toString());
   intent.putExtra("DESCRIPCION_RESERVA_ACTIVIDAD",descripcion);
   v.getContext().startActivity(intent);

  });

 }

 @Override
 public int getItemCount() {
  return lista.size();
 }

 public class ViewHolder extends RecyclerView.ViewHolder {

  private TextView horario,descripcion,ubicacion,fecha;
  private ImageView imagen;
  private LinearLayout layout;

  public ViewHolder(@NonNull View itemView) {
   super(itemView);

   horario = itemView.findViewById(R.id.tv_horario_mis_reservas);
   descripcion = itemView.findViewById(R.id.tv_tipo_actividad_mis_reservas);
   ubicacion = itemView.findViewById(R.id.tv_ubicacion_mis_reservas);
   fecha = itemView.findViewById(R.id.tv_fecha_mis_reservas);
   imagen = itemView.findViewById(R.id.iv_foto_mis_reservas);
   layout = itemView.findViewById(R.id.layoutPrincipal_mis_reservas);

  }
 }

 public void alternarColorRecyclerView(MisReservasActividadesAdapter.ViewHolder holder, int position){
  if(position % 2 == 0){
   holder.layout.setBackgroundResource(R.drawable.background_color_1);
  }else{
   holder.layout.setBackgroundResource(R.drawable.background_color_2);
  }
 }

}
