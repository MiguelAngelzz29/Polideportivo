package es.miguel.polideportivo_v2.adaptador;

import android.content.Context;
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
import es.miguel.polideportivo_v2.dominio.ReservaActividad;

public class SinReservasAdapter extends RecyclerView.Adapter<SinReservasAdapter.ViewHolder> {


 public SinReservasAdapter(){
 }

 @NonNull
 @Override
 public SinReservasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sin_reservas,parent,false);
  return new SinReservasAdapter.ViewHolder(view);
 }

 @Override
 public void onBindViewHolder(@NonNull SinReservasAdapter.ViewHolder holder, int position) {


 }

 @Override
 public int getItemCount() {
  return 1;
 }

 public class ViewHolder extends RecyclerView.ViewHolder {


  public ViewHolder(@NonNull View itemView) {
   super(itemView);


  }
 }
}
