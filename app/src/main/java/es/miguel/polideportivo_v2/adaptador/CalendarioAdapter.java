package es.miguel.polideportivo_v2.adaptador;



import static java.security.AccessController.getContext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import es.miguel.polideportivo_v2.R;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.ViewHolder> {

    private List<LocalDate> listaDias;
    private int selectedItem = -1;

    public CalendarioAdapter(List<LocalDate> listaDias) {
        this.listaDias = listaDias;
    }

    @NonNull
    @Override
    public CalendarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_dias,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarioAdapter.ViewHolder holder, int position) {
        holder.diaLetra.setText(listaDias.get(position).getDayOfWeek().toString());
        holder.diaNum.setText(""+listaDias.get(position).getDayOfMonth());
        holder.mes.setText(listaDias.get(position).getMonth().toString());

        if (selectedItem == position) {
            holder.cl.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
        } else {
            holder.cl.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }
       /* holder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int previousItem = selectedItem;
                selectedItem = holder.getBindingAdapterPosition();

                       RecyclerView rv4 = ((Activity) v.getContext()).findViewById(R.id.rv_listaHoras);
                        RecyclerView rv3 = ((Activity) v.getContext()).findViewById(R.id.rv3);
                        TextView tv3 = ((Activity) v.getContext()).findViewById(R.id.tv_horaGim);
                        if(rv3.getVisibility() == View.GONE) {
                            rv3.setVisibility(View.VISIBLE);
                            tv3.setVisibility(View.VISIBLE);
                        }else {
                            rv3.setVisibility(View.GONE);
                            tv3.setVisibility(View.GONE);
                        }



                // Desmarcar el elemento previamente seleccionado
                if (previousItem != -1) {
                    notifyItemChanged(previousItem);
                }

                // Marcar el elemento actualmente seleccionado
                notifyItemChanged(selectedItem);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listaDias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView diaLetra,diaNum,mes;
        private CardView cv;
        private ConstraintLayout cl;
        private boolean isPressed = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diaLetra = itemView.findViewById(R.id.tv_diaLetra);
            diaNum = itemView.findViewById(R.id.tv_diaNumero);
            mes = itemView.findViewById(R.id.tv_mes);
            cv = itemView.findViewById(R.id.cvDia);
            cl = itemView.findViewById(R.id.constraintDias);
        }
    }
}
