package com.driversuperdelivery.driversuperdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.driversuperdelivery.driversuperdelivery.modelos.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registrodedriver extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String FileName = "myfile";
    SharedPreferences prefs;

    private Uri photoURI;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private static final String TAG = "";
    Button subirfoto,registrousuario;
    ImageView foto_gallery;
    EditText nombres,apellidos,dni,telefono,contrasena,recontrasena,direccion,unidad;
    private String mCurrentPhotoPath;
    String nombrefoto;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrodedriver);
        subirfoto = (Button) findViewById(R.id.subirfoto);
        foto_gallery = (ImageView) findViewById(R.id.fotodriver);


        nombres = (EditText) findViewById(R.id.nombrecompleto);
        apellidos = (EditText) findViewById(R.id.apellidos);
        dni = (EditText) findViewById(R.id.dni);
        telefono = (EditText) findViewById(R.id.telefono);
        contrasena = (EditText) findViewById(R.id.contra);
        recontrasena = (EditText) findViewById(R.id.recontra);
        direccion = (EditText) findViewById(R.id.dire);
        unidad = (EditText) findViewById(R.id.placa);
        registrousuario = (Button) findViewById(R.id.registrousuario);

        registrousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetFragment bottomSheetDialog = BottomSheetFragment.newInstance();

                // String nombre = prefs.getString("nombreusuariof", "");

                Bundle bundle = new Bundle();
                bundle.putString("test", "Espera un momento por favor ");
                bundle.putString("nombreusuario", "");
                bundle.putString("imagen", "https://www.sodapop.pe/ii.gif");


                bottomSheetDialog.setArguments(bundle);
                bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

                final FirebaseStorage storage = FirebaseStorage.getInstance();




                StorageReference storageRef = storage.getReference();

                StorageReference mountainsRef = storageRef.child(nombres.getText().toString() + ".jpg");


                foto_gallery.setDrawingCacheEnabled(true);
                foto_gallery.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) foto_gallery.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        Driver subedriver = new Driver(1, nombres.getText().toString(), apellidos.getText().toString(), dni.getText().toString()
                                                , telefono.getText().toString(), contrasena.getText().toString()
                                                , direccion.getText().toString(), unidad.getText().toString(), nombrefoto, "idfire", "habilitado", "lati", "longi", "referencia", nombrefoto);

                                        new grabardriver().execute(subedriver);
                                    }
                                });
                            }
                        }


                    }
                });
            }
        });


        subirfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Registrodedriver.this);
                builder.setMessage("Elija una Opcion")
                        .setCancelable(false)
                        .setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(Registrodedriver.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Registrodedriver.this,
                                        Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Registrodedriver.this,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Registrodedriver.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                225);
                                    }


                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Registrodedriver.this,
                                            Manifest.permission.CAMERA)) {

                                    } else {
                                        ActivityCompat.requestPermissions(Registrodedriver.this,
                                                new String[]{Manifest.permission.CAMERA},
                                                226);
                                    }
                                } else {
                                    dispatchTakePictureIntent();
                                }
                            }


                        })

                        .setNegativeButton("Galeria de Fotos", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                openGallery();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
      private void checkExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e(TAG, "Permission not granted WRITE_EXTERNAL_STORAGE.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }
        }if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted CAMERA.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        226);
            }
        }

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Driver" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        nombrefoto=imageFileName;
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }




        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                foto_gallery.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras(); // Aquí es null
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mPhotoImageView.setImageBitmap(imageBitmap);
            }*/

        }

    }

    private class  grabardriver extends AsyncTask<Driver, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Driver ped;
        ProgressDialog pdLoading = new ProgressDialog(Registrodedriver.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tsubiendo driver.....");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Driver... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/apiguardardriver.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("GET");
                conne.setDoInput(true);
                conne.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("iddriver",String.valueOf(ped.getIddriver()))
                        .appendQueryParameter("nombresdriver",String.valueOf(ped.getNombresdriver()))
                        .appendQueryParameter("apellidosdriver",String.valueOf(ped.getApellidosdriver()))
                        .appendQueryParameter("dnidriver",String.valueOf(ped.getDnidriver()))
                        .appendQueryParameter("telefonodriver",String.valueOf(ped.getTelefonodriver()))
                        .appendQueryParameter("contrasenadriver",String.valueOf(ped.getContrasenadriver()))
                        .appendQueryParameter("direcciondriver",String.valueOf(ped.getDirecciondriver()))
                        .appendQueryParameter("placadriver", String.valueOf(ped.getPlacadriver()))
                        .appendQueryParameter("fotodriver",String.valueOf(ped.getFotodriver()))
                        .appendQueryParameter("idfirebase",String.valueOf(ped.getIdfirebase()))
                        .appendQueryParameter("estadodriver",String.valueOf(ped.getEstadodriver()))
                        .appendQueryParameter("latituddriver", String.valueOf(ped.getLatituddriver()))
                        .appendQueryParameter("longituddriver",String.valueOf(ped.getLongituddriver()))
                        .appendQueryParameter("referenciadriver",String.valueOf(ped.getReferenciadriver()))
                        .appendQueryParameter("foto",String.valueOf(ped.getFoto()))
                        ;
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
                Log.d("cirio",e1.toString());
                return null;
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
                    resultado=result.toString();
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("cirio2",e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            Log.d("cirio3",resultado);
            return resultado;

        }
        @Override
        protected void onPostExecute(final String resultado) {
            pdLoading.dismiss();
            super.onPostExecute(resultado);

                guardarensharesiyaestaregistrado(String.valueOf(ped.getIddriver()),String.valueOf(ped.getNombresdriver()),String.valueOf(ped.getApellidosdriver()),
                        String.valueOf(ped.getDnidriver()),String.valueOf(ped.getTelefonodriver()),String.valueOf(ped.getDirecciondriver()),
                        String.valueOf(ped.getPlacadriver()),String.valueOf(ped.getFotodriver())
                        ,String.valueOf(ped.getIdfirebase()),String.valueOf(ped.getEstadodriver()),String.valueOf(ped.getLatituddriver()),String.valueOf(ped.getLongituddriver())
                        ,String.valueOf(ped.getReferenciadriver()));

                Intent gy=new Intent(Registrodedriver.this,MainActivity.class);
                startActivity(gy);
        }
    }
    public   void guardarensharesiyaestaregistrado(
            String iddriver,String nombresdriver,String apellidosdriver,String dnidriver,String telefonodriver,
            String direcciondriver,String placadriver
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

}
