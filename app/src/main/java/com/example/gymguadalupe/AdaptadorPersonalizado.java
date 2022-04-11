package com.example.gymguadalupe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.vistaHolder>
{

    private Context context;
    private ArrayList<Objetivos> listaObjetivos = new ArrayList<>();

    public AdaptadorPersonalizado(Context c, ArrayList<Objetivos> lista){
        this.context = c;
        this.listaObjetivos = lista;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.vistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.diseno_fila,parent,false);
        return new vistaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.vistaHolder holder, int position) {
        holder.filaPeso.setText(listaObjetivos.get(position).getPeso_objetivo());
        holder.filaFecha.setText(listaObjetivos.get(position).getFecha_registro_objetivo()+"");
        holder.btnActualizarObjetivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context,OjetivosActivity.class);
                intent.putExtra("id",listaObjetivos.get(position).getId_objetivo()+"");
                intent.putExtra("espalda",listaObjetivos.get(position).getEspalda_objetivo()+"");
                intent.putExtra("cadera",listaObjetivos.get(position).getCadera_objetivo()+"");
                intent.putExtra("peso",listaObjetivos.get(position).getPeso_objetivo()+"");
                intent.putExtra("IMC",listaObjetivos.get(position).getIMC_objetivo()+"");
                intent.putExtra("cintura",listaObjetivos.get(position).getCintura_objetivo()+"");
                intent.putExtra("fecha",listaObjetivos.get(position).getFecha_registro_objetivo()+"");
            context.startActivity(intent);
            }
        });

        holder.btnEliminarObjetivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(listaObjetivos.get(position).getId_objetivo());

            }
        });
    }

    private void eliminar(String id_objetivo) {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmación");
        builder.setMessage("Desea elimar este registro");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child("Objetivos").child(id_objetivo).removeValue();
                context.startActivity(new Intent(context,ListadoObjetivosActivity.class));
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return listaObjetivos.size();
    }

    public class vistaHolder extends RecyclerView.ViewHolder {
        TextView filaPeso,filaFecha;
        ImageButton btnActualizarObjetivos, btnEliminarObjetivos;
        public vistaHolder(@NonNull View itemView){
            super(itemView);
            filaPeso = itemView.findViewById(R.id.filaPeso);
            filaFecha = itemView.findViewById(R.id.filaFecha);
            btnActualizarObjetivos = itemView.findViewById(R.id.btnActualizarObjetivos);
            btnEliminarObjetivos = itemView.findViewById(R.id.btnEliminarObjetivos);

        }
    }
}