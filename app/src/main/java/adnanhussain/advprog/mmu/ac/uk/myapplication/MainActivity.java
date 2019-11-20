package adnanhussain.advprog.mmu.ac.uk.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    Button button; //button variable
    String[] studentNames; //student array
    ArrayList<Student> allStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add =  (Button) findViewById(R.id.add); //add students button

        //run network on main thread hack
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView studentList = (ListView) findViewById(R.id.studentList);
        final HashMap<String, String>params = new HashMap <> ();


        //Making a http call
        HttpURLConnection urlConnection;
        InputStream in = null;

        //Opens the AddStudent class when the button is pressed
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AddStudent.class);
                startActivity(intent);
            }
        });

        try {
            // the url we wish to connect to
            URL url = new URL("http://radikaldesign.co.uk/sandbox/studentapi/getallstudents.php?apikey=7f42fba2c2");
            // open the connection to the specified URL
            urlConnection = (HttpURLConnection) url.openConnection();
            // get the response from the server in an input stream
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // covert the input stream to a string
        String response = convertStreamToString(in);

        // print the response to android monitor/log cat
        System.out.println("Server response = " + response);

        try {
            // declare a new json array and pass it the string response from the server
            // this will convert the string into a JSON array which we can then iterate
            // over using a loop
            JSONArray jsonArray = new JSONArray(response);

            // instantiate the student names array and set the size
            // to the amount of student objects returned by the server
            studentNames = new String[jsonArray.length()];

            // use a for loop to iterate over the JSON array
            for (int i=0; i < jsonArray.length(); i++)
            {
                //gets student details and stores it in a string variable
                String name = jsonArray.getJSONObject(i).get("name").toString();
                String gender = jsonArray.getJSONObject(i).get("gender").toString();
                String dob = jsonArray.getJSONObject(i).get("dob").toString();
                String address = jsonArray.getJSONObject(i).get("address").toString();
                String postcode = jsonArray.getJSONObject(i).get("postcode").toString();
                String studentNumber = jsonArray.getJSONObject(i).get("studentNumber").toString();
                String courseTitle = jsonArray.getJSONObject(i).get("courseTitle").toString();
                String startDate = jsonArray.getJSONObject(i).get("startDate").toString();
                String bursary = jsonArray.getJSONObject(i).get("bursary").toString();
                String email = jsonArray.getJSONObject(i).get("email").toString();

                // print the name to log cat
                System.out.println("name = " + name);
                //adds the fields into the students array
                Student s = new Student(name, gender, dob, address, postcode, studentNumber, courseTitle,
                        startDate, bursary, email);
                allStudents.add(s);

                // add the name of the current students to the studentNames array
                studentNames [i] = name;
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        //Creates Array Adapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames);
        studentList.setAdapter(arrayAdapter);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(MainActivity.this, "you pressed " + allStudents.get(i).getName(), Toast.LENGTH_SHORT).show();


                //opens DetailsActivity class
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

                // add/put the selected cheese object in to the intent which will
                // be passed over to the activity that is started
                // note we use a KEY:VALUE structure to pass variable/objects
                // between activities. Here the key is ‘cheese’ and the value is
                // the cheese object from the cheese array list using the position
                // which is specified by the ‘i’ variable.
                intent.putExtra("student", allStudents.get(i));

                // launch the activity
                startActivity(intent);
            }
        });

        //Delete Student
        //When the user long clicks on the student name, the student will be deleted
        studentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //toast message when the user clicks on a student name
                Toast.makeText(MainActivity.this, "clicked on" + allStudents.get(i).getName(), Toast.LENGTH_SHORT).show();
                params.put("apikey", "7f42fba2c2");
                params.put("studentnumber", allStudents.get(i).getStudentNumber());
                String url = "http://radikaldesign.co.uk/sandbox/studentapi/delete.php";
                performPostCall(url, params);
                return true;
            }
        });
    }

    public String performPostCall(String requestURL, HashMap postDataParams)
    {
        URL url;
        String response = "";
        try
        {
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

            //gets the server response code
            int responseCode=conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            //if the student is successfully deleted a toast message will appear or it will give you an error message
            if(responseCode == HttpsURLConnection.HTTP_OK)
            {
                Toast.makeText(this, "Student Deleted", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null)
                {
                    response+=line;
                }
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                response="";
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
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

    //METHOD
    public String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
