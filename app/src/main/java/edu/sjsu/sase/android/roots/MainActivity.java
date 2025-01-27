package edu.sjsu.sase.android.roots;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first check if  fragment is already added
        //otherwise, add LoginFragment
        if (savedInstanceState == null) {
            //create new instance of login frag, this entire thing is just to
            //redirect main to login frag since at the time i coded this there was no navbar
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, loginFragment);
            transaction.commit();
        }
    }
}
