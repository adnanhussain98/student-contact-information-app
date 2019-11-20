package adnanhussain.advprog.mmu.ac.uk.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get the intent
        Bundle extras = getIntent().getExtras();
        // create a cheese object from the cheese object that was passed over from
        // the MainActivity. Notice you use the key ('cheese') to retrieve thevalue/variable needed.
        final Student theStudent = (Student) extras.get("student");
        System.out.println("received from the intent: "+theStudent.getName());

        TextView heading = findViewById(R.id.textViewHeading);
        TextView gender = findViewById(R.id.textViewGender);
        TextView dob = findViewById(R.id.textViewDOB);
        TextView address = findViewById(R.id.textViewAddress);
        TextView postcode = findViewById(R.id.textViewPostcode);
        TextView studentNumber = findViewById(R.id.textViewStudentNumber);
        TextView courseTitle = findViewById(R.id.textViewCourseTitle);
        TextView startDate = findViewById(R.id.textViewStartDate);
        TextView bursary = findViewById(R.id.textViewBursary);
        TextView email = findViewById(R.id.textViewEmail);


        //gets the details from the student class and shows them in details
        heading.setText(theStudent.getName());
        gender.setText(theStudent.getGender());
        dob.setText(theStudent.getDob());
        address.setText(theStudent.getAddress());
        postcode.setText(theStudent.getPostcode());
        studentNumber.setText(theStudent.getStudentNumber());
        courseTitle.setText(theStudent.getCourseTitle());
        startDate.setText(theStudent.getStartDate());
        bursary.setText(theStudent.getBursary());
        email.setText(theStudent.getEmail());

        Button update =  (Button) findViewById(R.id.update);

        //Button to update a student
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UpdateStudent.class);
                intent.putExtra("studentDetails", theStudent);
                startActivity(intent);
            }
        });

    }
}
