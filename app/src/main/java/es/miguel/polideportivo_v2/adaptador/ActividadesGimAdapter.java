package es.miguel.polideportivo_v2.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.dominio.Actividad;

public class ActividadesGimAdapter extends RecyclerView.Adapter<ActividadesGimAdapter.ViewHolder> {

    private List<Actividad> listaActividades;

    public ActividadesGimAdapter(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    @NonNull
    @Override
    public ActividadesGimAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_gim,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesGimAdapter.ViewHolder holder, int position) {
        int imagenId = listaActividades.get(position).getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(listaActividades.get(position).getDescripcion());
        holder.seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView rv2 = ((Activity) v.getContext()).findViewById(R.id.rv2);
                TextView tv2 = ((Activity) v.getContext()).findViewById(R.id.tv_fechaGim);
                if(rv2.getVisibility() == View.GONE) {
                    rv2.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                }else{
                    rv2.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                }
            }
        });
        }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView descripcion;
        private Button seleccionar;
        private ImageView imagen;
        private RecyclerView rv;
        private Button btn_hora;
        private ConstraintLayout cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descripcion = itemView.findViewById(R.id.tv_tipo_deporte);
            seleccionar = itemView.findViewById(R.id.btn_seleccionar);
            imagen = itemView.findViewById(R.id.iv_foto);
            rv = itemView.findViewById(R.id.rv2);
            btn_hora = itemView.findViewById(R.id.btn_horario);
            cv =  itemView.findViewById(R.id.constraintDias);
        }
    }
}
