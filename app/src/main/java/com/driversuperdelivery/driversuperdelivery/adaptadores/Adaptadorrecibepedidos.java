package com.driversuperdelivery.driversuperdelivery.adaptadores;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driversuperdelivery.driversuperdelivery.CircleTransform;
import com.driversuperdelivery.driversuperdelivery.MainActivity;

import com.driversuperdelivery.driversuperdelivery.Map;
import com.driversuperdelivery.driversuperdelivery.R;
import com.driversuperdelivery.driversuperdelivery.modelos.Adicional;
import com.driversuperdelivery.driversuperdelivery.modelos.AdicionalRealm;

import com.driversuperdelivery.driversuperdelivery.modelos.AdicionalRealmFirebase;
import com.driversuperdelivery.driversuperdelivery.modelos.CremaRealm;
import com.driversuperdelivery.driversuperdelivery.modelos.CremaRealmFirebase;
import com.driversuperdelivery.driversuperdelivery.modelos.DetallepedidoRealmFirebase;
import com.driversuperdelivery.driversuperdelivery.modelos.Detallepedidorealm;
import com.driversuperdelivery.driversuperdelivery.modelos.Empresa;
import com.driversuperdelivery.driversuperdelivery.modelos.PedidoRealmFirebase;
import com.squareup.picasso.Picasso;

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
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Adaptadorrecibepedidos extends RecyclerView.Adapter<Adaptadorrecibepedidos.AdaptadorViewHolder>  {
    private MainActivity mainContext;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile";


    private static final String FPEDIDOS = "PEDIDOS";
    private static final String FDETALLEPEDIDO = "FDETALLEPEDIDO";
    private static final String FCREMAS = "FCREMAS";
    private static final String FADICIONAL = "FADICIONAL";

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private List<PedidoRealmFirebase> items;
    public Adaptadorrecibepedidos(ArrayList<PedidoRealmFirebase> items, MainActivity contexto){
        this.mainContext=contexto;
        this.items=items;


    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView nombre;
        protected TextView direccion,referencias,cuantopaga,vuelto,totalapagar1,idpedido,fechitapedido,estadopedido;
protected ToggleButton togle;
        protected TextView telefono,txtporentregar,nombreempresita;
        protected ImageView local;
        protected Button wasap,rechazarpedido,muestrapedido,mapau22,ordenentregada;

        public AdaptadorViewHolder(View v){
            super(v);
            this.local=(ImageView)v.findViewById(R.id.local);
            this.nombre=(TextView) v.findViewById(R.id.nombrecliente);
            this.direccion=(TextView) v.findViewById(R.id.direccion);
            this.totalapagar1=(TextView) v.findViewById(R.id.totalapagar1);
            this.telefono=(TextView) v.findViewById(R.id.telefono);
            this.referencias=(TextView) v.findViewById(R.id.referencias);
            this.cuantopaga=(TextView) v.findViewById(R.id.cuantopaga);
            this.vuelto=(TextView) v.findViewById(R.id.vuelto);
            this.idpedido=(TextView) v.findViewById(R.id.idpedido);
            this.wasap=(Button) v.findViewById(R.id.watsapp);

            this.fechitapedido=(TextView) v.findViewById(R.id.fechitapedido);

this.muestrapedido=(Button)v.findViewById(R.id.muestrapedido);
            this.mapau22=(Button)v.findViewById(R.id.mapau22);
            this.ordenentregada=(Button)v.findViewById(R.id.ordenentragadab);
            this.estadopedido=(TextView) v.findViewById(R.id.estadopeido);
            this.txtporentregar=(TextView) v.findViewById(R.id.txtporentregar);
            this.togle = (ToggleButton)v.findViewById(R.id.toggleButton2);
        }
    }
    @Override
    public Adaptadorrecibepedidos.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetitapedidoentrante,viewGroup,false);
        return new Adaptadorrecibepedidos.AdaptadorViewHolder(v);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final Adaptadorrecibepedidos.AdaptadorViewHolder viewHolder, final int position) {
        final PedidoRealmFirebase item = items.get(position);
        viewHolder.itemView.setTag(item);
        viewHolder.ordenentregada.setVisibility(View.VISIBLE);

        viewHolder.vuelto.setText(String.valueOf(item.getVuelto()));
        viewHolder.cuantopaga.setText(String.valueOf(item.getCuantopagaecliente()));
        viewHolder.referencias.setText(String.valueOf(item.getReferencias()));
        viewHolder.idpedido.setText(String.valueOf(item.getIdpedido()));
viewHolder.nombre.setText(String.valueOf(item.getNombreusuario()));
        viewHolder.direccion.setText(item.getDireccionallevar());
        viewHolder.totalapagar1.setText(String.valueOf(item.getTotalpedido()));
        viewHolder.telefono.setText(String.valueOf(item.getTelefono()));
viewHolder.fechitapedido.setText(String.valueOf(item.getFechapedido()));



        Picasso.get().load(String.valueOf(item.getIdfacebook())).transform(new CropSquareTransformation()).resize(400, 400).into(viewHolder.local);


viewHolder.local.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String hh=item.getIdempresa().toString();
        new trardatosdeempresa().execute(hh);

    }
});
viewHolder.togle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(viewHolder.togle.isChecked())
        {
            Toast.makeText(mainContext.getApplication(),"Pedido al driver",Toast.LENGTH_LONG).show();
            new vadriver().execute(String.valueOf(item.getIdpedido()),item.getIdempresa());

        }else{
            Toast.makeText(mainContext.getApplication(),"Movilidad local",Toast.LENGTH_LONG).show();
            new nodriver().execute(String.valueOf(item.getIdpedido()),item.getIdempresa());

        }
    }
});
if (item.getDescripcionpedido().toString().equals("null")){
    viewHolder.txtporentregar.setText("");

}else{

    viewHolder.txtporentregar.setText(String.valueOf(item.getDescripcionpedido()));
}


        switch(item.getEstadopedido().toString()) {
            case "entregado":
               viewHolder.estadopedido.setBackgroundColor(R.color.coloruno);
                viewHolder.estadopedido.setText("Entregado");
                //playButton.setVisibility(View.GONE);
                //stopButton.setVisibility(View.VISIBLE);

                viewHolder.ordenentregada.setVisibility(View.GONE);
                break;
            case "rechazado":

                viewHolder.estadopedido.setBackgroundColor(R.color.colorPrimary);
                viewHolder.estadopedido.setText("Rechazado");
                viewHolder.ordenentregada.setVisibility(View.GONE);
                break;
            case "generadodelivery":
                viewHolder.estadopedido.setBackgroundColor(R.color.chatotro);
                viewHolder.estadopedido.setText("Por entregar");
                viewHolder.ordenentregada.setVisibility(View.VISIBLE);
                break;

            default:
                // code block
        }

viewHolder.ordenentregada.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        String op=viewHolder.idpedido.getText().toString();
        new entregarpedido().execute(op);
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

    }
});

viewHolder.mapau22.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent i = new Intent().setClass(mainContext, Map.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        i.putExtra("longitud", item.getLongitud().toString());
        i.putExtra("latitud", item.getLatitud().toString());
        i.putExtra("nombre", item.getNombreusuario().toString());
        i.putExtra("direccion", item.getDireccionallevar().toString());
        i.putExtra("referencia", item.getReferencias().toString());

// Launch the new activity and add the additional flags to the intent
        mainContext.startActivity(i);

    }
});
viewHolder.muestrapedido.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        final Dialog dialog = new Dialog(mainContext);
        dialog.setTitle("Details");
        Realm pedido = Realm.getDefaultInstance();





        dialog.setContentView(R.layout.dialogo);
        RecyclerView detalles=(RecyclerView) dialog.findViewById(R.id.detalle);


        String hh=viewHolder.idpedido.getText().toString();

        ArrayList<DetallepedidoRealmFirebase> todoslosdetalles = new ArrayList<>();
todoslosdetalles.clear();
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .equalTo("idpedido", Integer.valueOf(hh))
                        .findAll();
        int w = results.size();
        DetallepedidoRealmFirebase detalleainsertar ;
        for (int i = 0; i < w; i++) {
            int cantidad = results.get(i).getCantidadrealm();
            int idd = results.get(i).getIddetallepedido();
            int idpedido = results.get(i).getIdpedido();
            String nombreproducto = results.get(i).getNombreproductorealm();
            String subtotal = results.get(i).getSubtotal();
            Double precvente = results.get(i).getPrecventarealm();
            int idproductorealm = results.get(i).getIdproductorealm();
            String comentariococina = results.get(i).getComentarioacocina();
            ArrayList<CremaRealmFirebase> todaslascremas = new ArrayList<>();
            RealmResults<CremaRealm> resulcremaao =
                    pedido.where(CremaRealm.class)
                            .equalTo("iddetalle", idd)
                            .findAll();
            int lsrgaa = resulcremaao.size();
            for (int ic = 0; ic < lsrgaa; ic++) {
                RealmResults<CremaRealm> resultsoa =
                        pedido.where(CremaRealm.class)
                                .equalTo("iddetalle", idd)
                                .findAll();
                CremaRealmFirebase dett = new CremaRealmFirebase(ic, resultsoa.get(ic).getNombrecrema().toString(), "1", 1, idproductorealm);
                todaslascremas.add(dett);
            }
            int g=todaslascremas.size();
            String fodat="";
            String foda="";
            for (int u=0;u<g;u++){
                foda=todaslascremas.get(u).getNombrecrema().toString();
                fodat=fodat+foda+"\r\n";



            }
////////////////////////////////////////////adicionales
            ArrayList<AdicionalRealmFirebase> todoslosadicionalesa = new ArrayList<>();
            RealmResults<AdicionalRealm> resulcremaad =
                    pedido.where(AdicionalRealm.class)
                            .equalTo("idadicionaldetalle", idd)
                            .findAll();
            int lsrgaad = resulcremaad.size();
            for (int ica = 0; ica < lsrgaad; ica++) {
                RealmResults<AdicionalRealm> resultsoad =
                        pedido.where(AdicionalRealm.class)
                                .equalTo("idadicionaldetalle", idd)
                                .findAll();
                AdicionalRealmFirebase dettd = new AdicionalRealmFirebase(ica,resultsoad.get(ica).getNombreadicional(),
                        Double.parseDouble(resultsoad.get(ica).getPrecioadicional().toString()),
                        resultsoad.get(ica).getEstadoadicional(),resultsoad.get(ica).getIdproducto(),resultsoad.get(ica).getId(),resultsoad.get(ica).getId());
                todoslosadicionalesa.add(dettd);
            }

            int ga=todoslosadicionalesa.size();
            String fodata="";
            String fodaa="";
            for (int ua=0;ua<ga;ua++){
                fodaa=todoslosadicionalesa.get(ua).getNombreadicional().toString();
                fodata=fodata+fodaa+"\r\n";
            }
            detalleainsertar = new DetallepedidoRealmFirebase(1,1  , cantidad, precvente, Double.parseDouble(subtotal), idpedido, 1, fodata, fodat, comentariococina,1,fodata,comentariococina,nombreproducto,"1");


            ///////////////////////////////////////////////////////





            todoslosdetalles.add(detalleainsertar);


            Adaptadorparaverenviarpedidos adapterproducto = new Adaptadorparaverenviarpedidos(todoslosdetalles,mainContext);
            detalles.setLayoutManager(new GridLayoutManager(mainContext, 1));
            detalles.setAdapter(adapterproducto);


        }






        dialog.show();
    }
});



        viewHolder.wasap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                  intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+viewHolder.telefono.getText().toString()+"&text="+"Hola, soy el driver que lleva tu pedido gracias por confiar en nosotros"));
                 mainContext.startActivity(intent);
                }catch (Exception e){
                  e.printStackTrace();
                }



            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }



    private class entregarpedido extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        //        ProgressDialog pdLoading = new ProgressDialog(mainContext.this);
        URL url = null;
        ArrayList<Adicional> listadeadicionales = new ArrayList<Adicional>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //          pdLoading.setMessage("\tCargando Adicionales");
            //        pdLoading.setCancelable(false);
            //      pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apipedidoentregado.php");
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
                        .appendQueryParameter("idpedido", params[0]);
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
            Log.d("paso",result.toString());
            //    pdLoading.dismiss();

            if (result.equals("no rows")) {
            } else {

            }
        }

    }



    private class vadriver extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        //        ProgressDialog pdLoading = new ProgressDialog(mainContext.this);
        URL url = null;
          @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //          pdLoading.setMessage("\tCargando Adicionales");
            //        pdLoading.setCancelable(false);
            //      pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/driverhabilitapedido.php");
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
                        .appendQueryParameter("idpedido", params[0])
                        .appendQueryParameter("idempresa", params[1])
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
            Log.d("paso",result.toString());
            //    pdLoading.dismiss();

            if (result.equals("true")) {
            } else {

            }
        }

    }

    private class nodriver extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        //        ProgressDialog pdLoading = new ProgressDialog(mainContext.this);
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //          pdLoading.setMessage("\tCargando Adicionales");
            //        pdLoading.setCancelable(false);
            //      pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/driverdesabilitapedido.php");
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
                        .appendQueryParameter("idpedido", params[0])
                        .appendQueryParameter("idempresa", params[1])
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
            Log.d("paso",result.toString());
            //    pdLoading.dismiss();

            if (result.equals("true")) {
            } else {

            }
        }

    }
    private class trardatosdeempresa extends AsyncTask<String, String, String> {

        HttpURLConnection conne;
        URL url = null;
        ArrayList<Empresa> listaalmaceno = new ArrayList<Empresa>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL("https://sodapop.pe/sugest/traerdatitodelaempresa.php");
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
                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("idempresa", params[0]);
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

            String[] strArrData = {"No Suggestions"};
            ArrayList<Empresa> people = new ArrayList<>();

            people.clear();
            RecyclerView.Adapter adapter;


            ArrayList<String> dataList = new ArrayList<String>();
            Empresa meso;
            if (result.equals("no rows")) {


            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        meso = new Empresa(


                                json_data.getInt("idempresa")
                                ,json_data.getString("razonsocialempresa")
                                ,json_data.getString("direccionempresa")
                                , json_data.getString("telefonoempresa")
                                ,json_data.getString("idhorarioempresa")
                                ,json_data.getString("emailempresa")
                                ,json_data.getString("paginawebempresa")
                                ,json_data.getString("estadoempresa")
                                ,json_data.getString("sloganempresa")
                                ,json_data.getString("nombreadministrador")
                                ,json_data.getString("telefonoadministrador")
                                , json_data.getString("logotipoempresa")
                                ,json_data.getString("idrubroempresa")
                                ,json_data.getString("tiempodedemoraempresa")
                                ,json_data.getString("costodelivery")
                                ,json_data.getString("latitudempresa")
                                ,json_data.getString("longitudempresa"))

                                ;
                        people.add(meso);
                    }
                    final Dialog dialog = new Dialog(mainContext);
                    dialog.setTitle("Details");

                    dialog.setContentView(R.layout.dialogolocal);

                    TextView nombie=(TextView) dialog.findViewById(R.id.nombreempre);
ImageView fot=(ImageView) dialog.findViewById(R.id.imageView8);
                    Button was=(Button)dialog.findViewById(R.id.wasapeada);
                    Button direcc=(Button)dialog.findViewById(R.id.direccionempresa);

                    nombie.setText(people.get(0).getRazonsocialempresa());
                    TextView dirio=(TextView) dialog.findViewById(R.id.dirio);
                    dirio.setText(people.get(0).getDireccionempresa());
                    Picasso.get().load(String.valueOf(String.valueOf(people.get(0).getIdrubroempresa()))).transform(new CropSquareTransformation()).resize(400, 400).into(fot);




                    Button w=(Button)dialog.findViewById(R.id.wasapeada);
                    w.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+51"+people.get(0).getTelefonoempresa().toString()+"&text="+"Hola, soy el driver que llevara tu pedido llegare en "));
                                mainContext.startActivity(intent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                    dialog.show();



                } catch (JSONException e) {

                }
            }
        }
    }
}

