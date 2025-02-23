package com.example.pap_projetofilmes_a26255;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Declarações de variáveis
    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização do FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Associação dos elementos de interface com as variáveis
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);

        // Obtém o usuário atualmente autenticado
        user = auth.getCurrentUser();

        // Verifica se o usuário está logado
        if (user == null){
            // Se não estiver logado, redireciona para a tela de login
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            // Se estiver logado, exibe os detalhes do usuário
            textView.setText(user.getEmail());
        }

        // Configuração do OnClickListener para o botão de logout
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Efetua o logout do usuário
                FirebaseAuth.getInstance().signOut();
                // Redireciona para a tela de login
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}
