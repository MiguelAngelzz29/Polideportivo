package es.miguel.polideportivo_v2.adaptador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.miguel.polideportivo_v2.R;

public class SinReservasAdapter extends RecyclerView.Adapter<SinReservasAdapter.ViewHolder> {

    public SinReservasAdapter() {
    }

    @NonNull
    @Override
    public SinReservasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_sin_reservas, parent, false);
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
