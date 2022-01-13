package com.example.loginwithmysql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private String result = "";
    private String UN;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        alert = new AlertDialog.Builder(MainActivity.this);
    }

    public void login(View view) {
        if (!username.getText().toString().isEmpty()) {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            loginAsyncTask loginTask = new loginAsyncTask();
            loginTask.execute(user, pass);
        } else
            alert.setTitle("Empty field")
                    .setMessage("Some fields are required")
                    .setPositiveButton("OK", null)
                    .setCancelable(false)
                    .create().show();
    }

    @SuppressLint("StaticFieldLeak")
    private class loginAsyncTask extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            UN = strings[0];
            String PW = strings[1];
            String[] field = new String[2];
            field[0] = "username";
            field[1] = "password";
            String[] data = new String[2];
            data[0] = UN;
            data[1] = PW;
            PutData putData = new PutData("http://192.168.1.3:80/Login_Android_Studio/login.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete())
                    result = putData.getResult();
            }
            return result;




            /*try {
                URL url = new URL(constructor);

                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream outputStream = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                        + "&&"+URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                outputStream.close();

                InputStream inputStream = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line = "";
                while((line = reader.readLine()) != null)
                    result += line;
                reader.close();
                inputStream.close();
                http.disconnect();
            } catch (IOException e) {
                result = e.getMessage();
            }*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Login Success")) {
                username.setText("");
                password.setText("");
                Intent intent = new Intent(MainActivity.this, AfterLogin.class);
                intent.putExtra("user", UN);
                startActivity(intent);
            } else {
                if (s.equals("Username or Password wrong")) {
                    alert.setTitle("Error Logging in")
                            .setMessage("Invalid username or password")
                            .setPositiveButton("OK", null)
                            .setCancelable(false)
                            .create().show();
                } else
                    alert.setTitle("Error!!")
                            .setMessage("Something went wrong...")
                            .setPositiveButton("OK", null)
                            .setCancelable(false)
                            .create().show();
            }
        }
    }
}