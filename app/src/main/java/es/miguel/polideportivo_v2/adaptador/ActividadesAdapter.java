package es.miguel.polideportivo_v2.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.dominio.Actividad;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> {

    private List<Actividad> listaActividades;

    public ActividadesAdapter(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    @NonNull
    @Override
    public ActividadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesAdapter.ViewHolder holder, int position) {
        int imagenId = listaActividades.get(position).getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(listaActividades.get(position).getDescripcion());
        holder.seleccionar.setOnClickListener( v -> {

                RecyclerView rv2 = ((Activity) v.getContext()).findViewById(R.id.rv2);
                RecyclerView rv3 = ((Activity) v.getContext()).findViewById(R.id.rv3);
                TextView tv2 = ((Activity) v.getContext()).findViewById(R.id.tv_fecha);
                TextView tv3 = ((Activity) v.getContext()).findViewById(R.id.tv_hora);
                if(rv2.getVisibility() == View.GONE) {
                    rv2.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    rv3.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);
                }else{
                    rv2.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                    rv3.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
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
