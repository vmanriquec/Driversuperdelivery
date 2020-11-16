package com.driversuperdelivery.driversuperdelivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.driversuperdelivery.driversuperdelivery.modelos.Driver;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.Nullable;

import io.github.dreierf.materialintroscreen.MaterialIntroActivity;
import io.github.dreierf.materialintroscreen.MessageButtonBehaviour;
import io.github.dreierf.materialintroscreen.SlideFragmentBuilder;


public class IntroActivity extends MaterialIntroActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    private static final int RC_SIGN_IN = 101;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    ImageView intro, cov;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colortres)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.logoimagen)
                        .title("Hola")
                        .description("Recuerda que brindas un servicio al publico y por lo tanto se amable, responsable, honesto y honrado.")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Juntos venceremos esta enfermedad");
                    }
                },
                        "Vamos tus pedidos te esperan  :)  "));






    }
    @Override
    public void onFinish() {
        super.onFinish();
        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);

        String nombre = prefs.getString("dnidriver", "");
        String estadopedido = prefs.getString("estadopedido", "");

                if (nombre.equals("")){
//                    iraregistrarusuarionuevo();
                    //registrodenuevousuario();
                    Intent i= new Intent(IntroActivity.this,Registrodedriver.class);
                    startActivity(i);
                }
                else{
                   Intent i= new Intent(IntroActivity.this,MainActivity.class);
                   startActivity(i);
                }
            }



    private void iraregistrarusuarionuevo() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false)
                .setIsSmartLockEnabled(!com.firebase.ui.auth.BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setLogo(R.mipmap.ic_launcher)
                .build();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @com.google.firebase.database.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                showAlertDialog(user);
            } else {
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
                Toast.makeText(getBaseContext(), "Algo Fallo", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                IntroActivity.this);
        mAlertDialog.create();
        // Showing Alert Message
        mAlertDialog.show();
        new   verificarsiusuarioexiste().execute(user.getUid());
        guardarsolotelefonoyiddefirebase(user.getPhoneNumber(),user.getUid());
        //      registrodenuevousuario();
    }
    private void veapedir() {
      //  Intent pi;
       // pi = new Intent(this,Muestraproductosporempresa.class);
        //startActivity(pi);
    }
    private void registrodenuevousuario() {
        Intent pi;
        pi = new Intent(this,Mostrarmapa.class);
        startActivity(pi);
    }
    public   void guardarsolotelefonoyiddefirebase(String telefono,String idfirebasedriver){
        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("telefonodriver",telefono);
        editor.putString("idfirebasedriver",idfirebasedriver);
        editor.commit();
    }

    public   void guardarensharesiyaestaregistrado(
            String iddriver,String nombresdriver,String apellidosdriver,String dnidriver,String telefonodriver,String direcciondriver,String placadriver
            ,String fotodriver,String idfirebasedriver,String estadodriver,String latituddriver,String longituddriver,String referenciadriver){
        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("iddriver",iddriver);
        editor.putString("nombresdriver",nombresdriver);
        editor.putString("apellidosdriver",apellidosdriver);
        editor.putString("dnidriver",dnidriver);
        editor.putString("telefonodriver",telefonodriver);
        editor.putString("direcciondriver",direcciondriver);
        editor.putString("placadriver",placadriver);
        editor.putString("fotodriver",fotodriver);
        editor.putString("idfirebasedriver",idfirebasedriver);

        editor.putString("estadodriver",estadodriver);
        editor.putString("latituddriver",latituddriver);
        editor.putString("longituddriver",longituddriver);
        editor.putString("referenciadriver",referenciadriver);




        editor.commit();

    }
    private class verificarsiusuarioexiste extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        ProgressDialog pdLoading = new ProgressDialog(IntroActivity.this);
        URL url = null;
         @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tvalidando   Driver...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraerdriverporfirebase.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);

                conne.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("idfirebase", params[0]);
                String query = builder.build().getEncodedQuery();
                // Open connection for sending data
                OutputStream os = conne.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conne.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conne.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conne.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (
                            result.toString()
                    );
                } else {
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conne.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            // Animation a = AnimationUtils.loadAnimation(getApplication(), R.anim.dechicoagrande);
            //a.reset();
            pdLoading.dismiss();
            ArrayList<Driver> peopleadicional = new ArrayList<>();
            String[] stradicional = {"No Suggestions"};
            ArrayList<String> datalistadicional = new ArrayList<String>();
            Driver mesoadiconal;
            peopleadicional.clear();
            if (result.equals("null")) {
                registrodenuevousuario();


            }


            else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoadiconal = new Driver(



                                json_data.getInt("iddriver"),
                                json_data.getString("nombresdriver"),json_data.getString("apellidosdriver")
                                ,json_data.getString("dnidriver"),json_data.getString("telefonodriver"),json_data.getString("contrasenadriver")
                                ,json_data.getString("direcciondriver"),json_data.getString("placadriver"),json_data.getString("fotodriver")
                                ,json_data.getString("estadodriver"),json_data.getString("idfirebase")
                                ,json_data.getString("latituddriver")
                                ,json_data.getString("longituddriver"),json_data.getString("referenciadriver")
                                ,json_data.getString("foto")
                        );

                        peopleadicional.add(mesoadiconal);
                    }

                    if(peopleadicional.get(0).getTelefonodriver().equals(""))
                    {
                        guardarsolotelefonoyiddefirebase(peopleadicional.get(0).getTelefonodriver(),peopleadicional.get(0).getIdfirebase());
                        registrodenuevousuario();
                    }else{



                        guardarensharesiyaestaregistrado(String.valueOf(peopleadicional.get(0).getIddriver()),peopleadicional.get(0).getNombresdriver(),
                                peopleadicional.get(0).getApellidosdriver(),peopleadicional.get(0).getDnidriver(),peopleadicional.get(0).getTelefonodriver(),
                                peopleadicional.get(0).getDirecciondriver(),peopleadicional.get(0).getPlacadriver()
                                ,peopleadicional.get(0).getFotodriver(),peopleadicional.get(0).getIdfirebase(),

                                peopleadicional.get(0).getEstadodriver(),peopleadicional.get(0).getLatituddriver()
                                ,peopleadicional.get(0).getLongituddriver(),peopleadicional.get(0).getReferenciadriver()

                        );

                        Toast.makeText(getApplicationContext(),"Hola ya estas registrado, te mostrare los pedidos ahora ",Toast.LENGTH_LONG).show();
                        veapedir();
                    }


                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }

    }
    @Override
    public void onBackPressed() {
    }
}







