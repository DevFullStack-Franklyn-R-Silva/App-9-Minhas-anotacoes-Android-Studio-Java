package com.hadesfranklyn.minhasanotacoes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.hadesfranklyn.minhasanotacoes.databinding.ActivityMainBinding;
import com.hadesfranklyn.minhasanotacoes.model.AnotacaoPreferencias;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private AnotacaoPreferencias preferencias;
    private EditText editAnotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        editAnotacao = findViewById(R.id.editAnotacao);

        preferencias = new AnotacaoPreferencias(getApplicationContext());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validar se foi digitado algo
                String textoRecuperado = editAnotacao.getText().toString();
                if (textoRecuperado.equals("")) {
                    Snackbar.make(view, "Preencha a anotação!", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    preferencias.salvarAnotacao(textoRecuperado);
                    Snackbar.make(view, "Anotação salva com sucesso!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
        //Recuperar anotacao
        String anotacao = preferencias.recuperarAnotacao();
        if (!anotacao.equals("")) {
            editAnotacao.setText(anotacao);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}