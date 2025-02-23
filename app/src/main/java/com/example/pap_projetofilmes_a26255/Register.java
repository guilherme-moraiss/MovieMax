package com.example.pap_projetofilmes_a26255;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    // Declarações de variáveis
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicialização do FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Associação dos elementos de interface com as variáveis
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        // Configuração do OnClickListener para o texto de login
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela de login
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        // Configuração do OnClickListener para o botão de registro
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Exibe a barra de progresso
                progressBar.setVisibility(View.VISIBLE);

                // Obtém o email e senha inseridos pelo usuário
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Verifica se o email ou senha estão vazios
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                // Cria uma nova conta de usuário no Firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Esconde a barra de progresso após a conclusão
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Se o registro for bem-sucedido, envia o e-mail de verificação
                                    sendEmailVerification();
                                    Toast.makeText(Register.this, "Verificação de email enviada.",
                                            Toast.LENGTH_SHORT).show();
                                    // Abre a tela de login
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    finish();
                                } else {
                                    // Se houver falha no registro, exibe uma mensagem de erro
                                    Toast.makeText(Register.this, "Falha na autenticação",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Método para enviar o e-mail de verificação
    private void sendEmailVerification() {
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Exibe uma mensagem se o e-mail de verificação for enviado com sucesso
                            Toast.makeText(Register.this, "Verificação de email enviada.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Exibe uma mensagem se houver falha no envio do e-mail de verificação
                            Toast.makeText(Register.this, "Falha ao enviar email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}