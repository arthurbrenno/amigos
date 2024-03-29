package com.brc.amigos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DbAmigosAdapter extends RecyclerView.Adapter<DbAmigosHolder> {

    private final List<DbAmigo> amigos;

    public DbAmigosAdapter(List<DbAmigo> amigos) {
        this.amigos = amigos;
    }

    // Este método retorna o layout criado pela ViewHolder, inflado numa view

    @Override
    public DbAmigosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DbAmigosHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dados_amigo, parent, false));
    }


// Recebe a ViewHolder e a posição da lista, de forma que um objeto da lista é recuperado pela posição e associado a ela - é o foco da ação para acontecer o processo

    @Override
    public void onBindViewHolder(DbAmigosHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nmAmigo.setText(amigos.get(position).getNome());
        holder.vlCelular.setText(amigos.get(position).getCelular());

        holder.btnEditar.setOnClickListener(v -> {
            Activity activity = getActivity(v);
            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("amigo", amigos.get(position));
            activity.finish();
            activity.startActivity(intent);
        });

        final DbAmigo amigo = amigos.get(position);
        holder.btnExcluir.setOnClickListener(v -> {
            final View view = v;
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Confirmação")
                    .setMessage("Tem certeza que deseja excluir o amigo ["+amigo.getNome().toString()+"]?")
                    .setPositiveButton("Excluir", (dialog, which) -> {
                        DbAmigo amigo1 = amigos.get(position);
                        DbAmigosDAO dao = new DbAmigosDAO(view.getContext());
                        boolean sucesso = dao.excluir(amigo1.getId());
                        if(sucesso) {
                            Snackbar.make(view, "Excluindo o amigo ["+ amigo1.getNome().toString()+"]!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            excluirAmigo(amigo1);
                        }else{
                            Snackbar.make(view, "Erro ao excluir o amigo ["+ amigo1.getNome().toString()+"]!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create()
                    .show();
        });

    }
// Esta função retorna a quantidade de itens que há na lista. É importante verificar se a lista possui elementos, para não causar um erro de exceção.

    @Override
    public int getItemCount() {
        return amigos != null ? amigos.size() : 0;
    }

    public void inserirAmigo(DbAmigo amigo){
        amigos.add(amigo);
        notifyItemInserted(getItemCount());
    }

    public void atualizarAmigo(DbAmigo amigo){
        amigos.set(amigos.indexOf(amigo), amigo);
        notifyItemChanged(amigos.indexOf(amigo));
    }

    public void excluirAmigo(DbAmigo amigo)
    {
        int position = amigos.indexOf(amigo);
        amigos.remove(position);
        notifyItemRemoved(position);
    }


    private Activity getActivity(View view) {
        Context context = view.getContext();

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }


}
