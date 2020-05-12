package com.example.ui_01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends  AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton btn_google; //구글 로그인 버튼
    private FirebaseAuth auth; //파베 인증 객체
    private GoogleApiClient googleApiClient;
    private static final int REO_SIGN_GOOGLE = 100;//구글 로그인 결과 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance();//파베 인증 객체 초기화
        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {//구글 로그인 버튼을 클릭시 수행
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REO_SIGN_GOOGLE);//결과를 받아
            }
        });
    }

    //구글 로그인 인증을 요청했을때 결과 값을 되돌려 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REO_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){//인증 성공
                GoogleSignInAccount account = result.getSignInAccount();
                //account라는 데이터 = 구글로그인 정보를 담음(닉, 프로필사진uri,이메일주소등)
                resultLogin(account); //로그인 결과값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //인증에 진짜 성공했나?
                        if(task.isSuccessful()){//성공
                            Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),FragHome.class);
                            intent.putExtra("id",account.getDisplayName());//구글닉네임을 가져옴
                            intent.putExtra("photourl",String.valueOf(account.getPhotoUrl()));

                            //String.valueof 특정 자료형을 String 형태로 변환

                            startActivity(intent);

                        }else{//실패
                            Toast.makeText(LoginActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

