package adnanhussain.advprog.mmu.ac.uk.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import javax.net.ssl.HttpsURLConnection;

public class AddStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        //run network on main thread hack
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final EditText name = (EditText) findViewById(R.id.addName);
        final EditText gender = (EditText) findViewById(R.id.addGender);
        final EditText dob = (EditText) findViewById(R.id.addDOB);
        final EditText address = (EditText) findViewById(R.id.addAddress);
        final EditText postcode = (EditText) findViewById(R.id.addPostcode);
        final EditText studentNumber = (EditText) findViewById(R.id.addStudentNumber);
        final EditText courseTitle = (EditText) findViewById(R.id.addCourseTitle);
        final EditText startDate = (EditText) findViewById(R.id.addStartDate);
        final EditText bursary = (EditText) findViewById(R.id.addBursary);
        final EditText email = (EditText) findViewById(R.id.addEmail);

        //finds the add student button
        Button button = (Button) findViewById(R.id.addStudent);

        final HashMap<String, String> params = new HashMap<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String Name = name.getText().toString();
                String Gender = gender.getText().toString();
                String Dob = dob.getText().toString();
                String Address = address.getText().toString();
                String Postcode = postcode.getText().toString();
                String StudentNumber = studentNumber.getText().toString();
                String CourseTitle = courseTitle.getText().toString();
                String StartDate = startDate.getText().toString();
                String Bursary = bursary.getText().toString();
                String Email = email.getText().toString();

                Student s = new Student(Name, Gender, Dob, Address, Postcode, StudentNumber, CourseTitle,
                        StartDate, Bursary, Email);
                String StudentJson = gson.toJson(s);
                System.out.println(StudentJson);
                params.put("apikey","7f42fba2c2");
                params.put("json", StudentJson);
                String url = "http://radikaldesign.co.uk/sandbox/studentapi/add.php";
                performPostCall(url, params);
            }
        });
    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams)
    {
        URL url;
        String response = "";
        try{
            url = new URL(requestURL);

            //create the connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //post data to connection
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            //post value data encoded
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            //get the server response code
            int responseCode=conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            if(responseCode == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                response="";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
