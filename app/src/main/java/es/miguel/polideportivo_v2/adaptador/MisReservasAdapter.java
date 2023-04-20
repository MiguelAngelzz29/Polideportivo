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
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ViewHolder> {

 private List<ReservaPista> lista;
 private String email;
 private Context context;

 public MisReservasAdapter(List<ReservaPista> lista, String email, Context context) {
  this.lista = lista;
  this.email = email;
  this.context = context;
 }

 @NonNull
 @Override
 public MisReservasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mis_reservas,parent,false);
  return new MisReservasAdapter.ViewHolder(view);
 }

 @Override
 public void onBindViewHolder(@NonNull MisReservasAdapter.ViewHolder holder, int position) {

  String url = lista.get(position).getPista().getImagen();
  Glide.with(context)
          .load(url)
          .into(holder.imagen);
  holder.descripcion.setText(lista.get(position).getPista().getTipo_deporte());
  holder.horario.setText(lista.get(position).getHorario_reservado());
  holder.ubicacion.setText(lista.get(position).getPista().getUbicacion());
  holder.fecha.setText(lista.get(position).getFecha_reserva().toString());

  alternarColorRecyclerView(holder,position);

  holder.layout.setOnClickListener( v -> {

   // implementar un layout para anular reservas

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

 public void alternarColorRecyclerView(MisReservasAdapter.ViewHolder holder, int position){
  if(position % 2 == 0){
   holder.layout.setBackgroundResource(R.drawable.background_color_1);
  }else{
   holder.layout.setBackgroundResource(R.drawable.background_color_2);
  }
 }

}