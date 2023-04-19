package es.miguel.polideportivo_v2.adaptador;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.tools.ant.Main;

import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.activity.MainActivity;
import es.miguel.polideportivo_v2.activity.ReservaActivity;
import es.miguel.polideportivo_v2.dominio.Actividad;

public class SeleccionarActividadAdapter extends RecyclerView.Adapter<SeleccionarActividadAdapter.ViewHolder> {

    private List<Actividad> listaActividades;
    private String email;


    public SeleccionarActividadAdapter(List<Actividad> listaActividades, String email) {
        this.listaActividades = listaActividades;
        this.email = email;
    }

    @NonNull
    @Override
    public SeleccionarActividadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_seleccionar_actividades,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeleccionarActividadAdapter.ViewHolder holder, int position) {
        int imagenId = listaActividades.get(position).getImagen();
        holder.imagen.setImageResource(imagenId);
        holder.descripcion.setText(listaActividades.get(position).getDescripcion());


        alternarColorRecyclerView(holder,position);

        holder.layout.setOnClickListener( v -> {

            Intent intent = new Intent(v.getContext(), ReservaActivity.class);
            intent.putExtra("IMAGEN_ACTIVIDAD",listaActividades.get(position).getImagen());
            intent.putExtra("DESCRIPCION_ACTIVIDAD",listaActividades.get(position).getDescripcion());
            intent.putExtra("EMAIL_ACTIVIDAD",this.email);
            v.getContext().startActivity(intent);

        });

        }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView horario,descripcion;
        private ImageView imagen;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            descripcion = itemView.findViewById(R.id.tv_tipo_actividad_selec);
            imagen = itemView.findViewById(R.id.iv_foto_actividad_selec);
            layout = itemView.findViewById(R.id.layout_rv_seleccionar_actividad);

        }
    }

    public void alternarColorRecyclerView(SeleccionarActividadAdapter.ViewHolder holder, int position){
        if(position % 2 == 0){
            holder.layout.setBackgroundResource(R.drawable.background_color_1);
        }else{
            holder.layout.setBackgroundResource(R.drawable.background_color_2);
        }
    }

    }
