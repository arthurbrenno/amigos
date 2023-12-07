package com.brc.amigos;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DbAmigosHolder extends RecyclerView.ViewHolder {
    public TextView nmAmigo;
    public TextView    vlCelular;
    public Button btnEditar;
    public Button btnExcluir;

    public DbAmigosHolder(View itemView) {
        super(itemView);
        nmAmigo = (TextView) itemView.findViewById(R.id.nmAmigo);
        vlCelular = (TextView) itemView.findViewById(R.id.vlCelular);
        btnEditar = (android.widget.Button) itemView.findViewById(R.id.btnEditar);
        btnExcluir = (android.widget.Button) itemView.findViewById(R.id.btnExcluir);
    }
}

