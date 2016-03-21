package com.dosomething.android.CommonClasses;

/**
 * Created by hi on 10-Jul-15.
 */
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Jsonfunctions {
    private static final int TIMEOUT = 1000 * 60;
    // the timeout until a connection is established
    private static final int CONNECTION_TIMEOUT = 1000 * 60;
    // the timeout for waiting for data
    private static final int SOCKET_TIMEOUT = 1000 * 60;
    // from ClientConnectionRequest
    private static final long MCC_TIMEOUT = 1000 *60;
    private Context context;

    public Jsonfunctions (Context context) {
        this.context = context;
    }

    public final String getJSONfromURL(String url){
        InputStream is = null;
        String result = "";
        if(isConnected()) {
            //JSONObject jArray = null;

            DefaultHttpClient client;

            //prepare for the https connection
            //call this in the constructor of the class that does the connection if
            //it's used multiple times
            KeyStore trustStore;
            SSLSocketFactory sf = null;
            try {
                trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                sf = new CustomSSLSocketFactory(trustStore);
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            assert sf != null;
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            // Setting up parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");
            params.setBooleanParameter("http.protocol.expect-continue", false);

            // Setting timeout
            HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, TIMEOUT);

            // Registering schemes for both HTTP and HTTPS
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            // Creating thread safe client connection manager
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            // Creating HTTP client
            client = new DefaultHttpClient(ccm, params);

            // Registering user name and password for authentication
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(null, -1),
                    new UsernamePasswordCredentials("", ""));

            //http post
            try{
                url = convertSpaceToEncode(url);
                Log.d("Web URL",url);
                HttpGet get = new HttpGet(url);
                setTimeouts(get.getParams());
                HttpResponse response = client.execute(get);

                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
            }

            //convert response to string
            try{
                assert is != null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
                result=sb.toString();
                Log.d("Web Responce", result);
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }

		    /*try{
	            jArray = new JSONObject(result);
		    }catch(JSONException e){
		    	Log.e("log_tag", "Error parsing data "+e.toString());
		    }*/
        } else {
            Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Error");
            builder.setMessage("No internet connection");
            builder.setCancelable(true);
            builder.setPositiveButton("cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return result;
    }

    public final String postToURL(String url, HashMap<String, Object> val){
        InputStream is = null;
        String result = "";
        DefaultHttpClient client;

        Log.d("POST VALUES", val.toString());

        //prepare for the https connection
        //call this in the constructor of the class that does the connection if
        //it's used multiple times
        KeyStore trustStore;
        SSLSocketFactory sf = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new CustomSSLSocketFactory(trustStore);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        assert sf != null;
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        // Setting up parameters
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        params.setBooleanParameter("http.protocol.expect-continue", false);

        // Setting timeout
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT);

        // Registering schemes for both HTTP and HTTPS
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", sf, 443));

        // Creating thread safe client connection manager
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

        // Creating HTTP client
        client = new DefaultHttpClient(ccm, params);

        // Registering user name and password for authentication
        client.getCredentialsProvider().setCredentials(
                new AuthScope(null, -1),
                new UsernamePasswordCredentials("", ""));

        //http post
        try{
            url = convertSpaceToEncode(url);
            Log.d("Web URL",url);
            HttpPost post = new HttpPost(url);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            Iterator myVeryOwnIterator = val.keySet().iterator();
            while(myVeryOwnIterator.hasNext()) {
                String key=(String)myVeryOwnIterator.next();
                String value=(String)val.get(key);
                nameValuePairs.add(new BasicNameValuePair(key, value));
            }
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            setTimeouts(post.getParams());
            HttpResponse response = client.execute(post);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }

        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            result=sb.toString();
            Log.d("Web Responceeeee", result);
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        return result;
    }

    public String postImageWebservice(String url, HashMap<String, String> val, Bitmap bitmap, String valimg) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);

//			Log.d("POST VALUES", "===="+val.toString());
//			Log.d("POST URL", "===="+url);



            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                Log.d("POST DATA", "===="+data);
                entity.addPart(valimg, new ByteArrayBody(data, "bill.jpg"));
                Log.d("POST ENTITY", "===="+entity);
            } catch(Exception e) {
                e.printStackTrace();
            }
            Iterator myVeryOwnIterator = val.keySet().iterator();
            while(myVeryOwnIterator.hasNext()) {
                String key=(String)myVeryOwnIterator.next();
                String value=(String)val.get(key);
                entity.addPart(key, new StringBody(value));
            }

            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost,
                    localContext);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent(), "UTF-8"));

            String sResponse = reader.readLine();
            Log.d("Responce", "===="+sResponse);
            return sResponse;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
            return "";
        }
    }

    private static void setTimeouts(HttpParams params) {
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                CONNECTION_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT);
        params.setLongParameter(ConnManagerPNames.TIMEOUT, MCC_TIMEOUT);
    }

    private class CustomSSLSocketFactory extends SSLSocketFactory {
        public SSLContext sslContext = SSLContext.getInstance("TLS");
        public CustomSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[] {tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

    }

    public String convertSpaceToEncode(String str) {
        String url = null;
        try{
            url = new String(str.trim().replace(" ", "%20"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    /*** Check the Internet connectivity ***/
    public boolean isConnected()
    {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}

